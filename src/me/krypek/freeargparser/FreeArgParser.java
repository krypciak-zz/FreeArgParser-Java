package me.krypek.freeargparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FreeArgParser {

	public static void main(final String[] args) {
		//@f:off
		final String input = """
			-i 1 \
			-d 2.3 \
			-s susibaka \
			-b true \
			-na \
			-intA [1, 2, 3, 4, 5] \
			-strA [hello guys, "hi bro", yooooo] \
			-douA [2.1, 1.3, 3.7, 7] \
			-booA [0, 0, 0, 1, 0, 1] """;

		final ParsedData data = new ParserBuilder()
				.add("i", 	"int", 		true, 	ArgType.Int, 		"integer sus")
				.add("d", 	"double", 	true, 	ArgType.Double,		"even more sus double")
				.add("s", 	"string", 	true, 	ArgType.String,		"sussiest amoguse stringu")
				.add("b", 	"boolean", 	true, 	ArgType.Boolean,	"bul bul bul")
				.add("na", 	"noarg",	false,	ArgType.None, 		"no arg test")
				.add("intA","intArray", true, 	ArgType.IntArray, 	" yo")
				.add("strA","stringArray", true, 	ArgType.StringArray, 	" yo")
				.add("douA","doubleArray", true, 	ArgType.DoubleArray, 	" yo")
				.add("booA", "booleanArray", true, ArgType.BooleanArray, "yoo")

				.parse(input);


		System.out.println("\nint:\t\t"+data.getInt("i")+
						   "\ndouble:\t\t"+data.getDouble("d")+
						   "\nstring:\t\t"+data.getString("s")+
						   "\nboolean:\t"+data.getBoolean("b")+
						   "\nnoarg:\t\t"+data.has("na")+
						   "\nintA:\t\t"+Arrays.toString(data.getIntArray("intA"))+
						   "\nstrA:\t\t"+Arrays.toString(data.getStringArray("strA"))+
						   "\ndouA:\t\t"+Arrays.toString(data.getDoubleArray("douA"))+
						   "\nbooA:\t\t"+Arrays.toString(data.getBooleanArray("booA"))
				);
		//@f:on
		System.out.println(data);
	}

	private final HashMap<String, BuilderArg> shortNameMap;
	private final HashMap<String, String> nameMapToShort;

	private HashMap<String, Argument> argumentMap;

	public FreeArgParser(final HashMap<String, BuilderArg> shortNameMap, final HashMap<String, String> nameMapToShort) {
		this.shortNameMap = shortNameMap;
		this.nameMapToShort = nameMapToShort;
	}

	public ParsedData parse(final String[] args) {
		final StringBuilder sb = new StringBuilder();
		for (final String str : args) { sb.append(str); sb.append(" "); }
		return parse(sb.toString());
	}

	public ParsedData parse(String str) {
		if(!str.endsWith(" "))
			str += " ";

		argumentMap = new HashMap<>();

		final char[] chars = str.toCharArray();
		boolean isShortName = false, isName = false, isArg = false, isQuote = false, firstInArg = false, isArgShort = false;
		String currentName = "";
		int nameIndex = 0;
		String argStr = "";
		int argIndex = 0;

		int bracket = 0;

		for (int i = 0; i < chars.length; i++) {
			final char c = chars[i];
			final char next = i == chars.length - 1 ? '?' : chars[i + 1];
			final char prev = i == 0 ? '?' : chars[i - 1];

			if(isShortName || isName) {
				if(c == ' ') {
					isShortName = false;
					isName = false;
					currentName = str.substring(nameIndex, i);

					for (; i < chars.length; i++) {
						final char c1 = chars[i];
						if(c1 == '-' || i == chars.length - 1)
							resolveArgument(currentName, isArgShort, "");
						else if(c1 == ' ')
							continue;

						isArg = true;
						firstInArg = true;
						break;
					}
					i--;
				}
				continue;
			}

			if(isArg) {
				if(c == ' ' && firstInArg)
					continue;
				if(c == ' ' && !isQuote && bracket == 0) {
					isArg = false;
					argStr = str.substring(argIndex, i);
					resolveArgument(currentName, isArgShort, argStr);
					continue;
				}

				if(c == '[')
					bracket++;
				if(c == ']')
					bracket--;

				if(c == '"' && bracket == 0) {
					if(firstInArg) {
						firstInArg = false;
						isQuote = true;
						argIndex = i;
						continue;
					}

					if(prev == '\\')
						continue;

					if(isQuote) {
						isQuote = false;
						isArg = false;
						argStr = str.substring(argIndex, i + 1);
						resolveArgument(currentName, isArgShort, argStr);
					}

					continue;
				}
				if(firstInArg) {
					argIndex = i;
					firstInArg = false;
				}
			}

			if(c == '-' && bracket == 0) {
				if(next == '-') {
					isName = true;
					isShortName = false;
					isArgShort = false;
					i++;
				} else {
					isName = false;
					isShortName = true;
					isArgShort = true;
				}
				nameIndex = i + 1;
				continue;
			}
		}

		final List<String> missing = new ArrayList<>();
		shortNameMap.forEach((k, v) -> {
			if(v.isRequired && !argumentMap.containsKey(k))
				missing.add(k);
		});
		if(missing.size() != 0) {
			final StringBuilder sb = new StringBuilder();
			for (final String str1 : missing) { sb.append("\"-"); sb.append(str1); sb.append("\", "); }
			final String missingStr = sb.toString();
			HelpMessage.print("Please provide the required argument" + (missing.size() > 1 ? "s" : "") + ": "
					+ missingStr.substring(0, missingStr.length() - 3) + "\".", shortNameMap, nameMapToShort);
		}
		return new ParsedData(argumentMap);
	}

	private void resolveArgument(final String name, final boolean isShort, final String str) {
		ArgType argTypeExpected;
		String newName;

		if(isShort) {
			if(!shortNameMap.containsKey(name))
				HelpMessage.print("Argument: \"-" + name + "\" is invalid.", shortNameMap, nameMapToShort);
			argTypeExpected = shortNameMap.get(name).argType;
			newName = name;
		} else {
			if(!nameMapToShort.containsKey(name))
				HelpMessage.print("Argument: \"--" + name + "\" is invalid.", shortNameMap, nameMapToShort);
			newName = nameMapToShort.get(name);
			argTypeExpected = shortNameMap.get(newName).argType;
		}

		final Argument arg = switch (argTypeExpected) {
		case Int -> {
			final Integer resolved = resolveInt(str);
			if(resolved == null)
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected type int, insted got: \"" + str + "\".", shortNameMap,
						nameMapToShort);
			yield new Argument(resolved);
		}
		case Boolean -> {
			final Boolean resolved = resolveBoolean(str);
			if(resolved == null)
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected type boolean, insted got: \"" + str + "\".", shortNameMap,
						nameMapToShort);
			yield new Argument(resolved);
		}
		case Double -> {
			final Double resolved = resolveDouble(str);
			if(resolved == null)
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected type double, insted got: \"" + str + "\".", shortNameMap,
						nameMapToShort);
			yield new Argument(resolved);
		}
		case String -> new Argument(resolveString(str));

		case StringArray -> {
			if((!str.startsWith("[") || !str.endsWith("]")))
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name
						+ "\"  String array has to start with \'[\' and end with \']\', insted got: \"" + str + "\".", shortNameMap, nameMapToShort);

			final String[] strA = getArrayFromString(str);
			for (int i = 0; i < strA.length; i++)
				strA[i] = resolveString(strA[i]);
			yield new Argument(strA);
		}

		case BooleanArray -> {
			if((!str.startsWith("[") || !str.endsWith("]")))
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name
						+ "\"  Boolean array has to start with \'[\' and end with \']\', insted got: \"" + str + "\".", shortNameMap, nameMapToShort);

			final String[] strA = getArrayFromString(str);
			final boolean[] boolA = new boolean[strA.length];
			for (int i = 0; i < strA.length; i++) {
				final Boolean bool = resolveBoolean(strA[i]);
				if(bool == null)
					HelpMessage.print(
							"Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected type boolean, insted got: \" [ ... " + strA[i] + " ... ]\".",
							shortNameMap, nameMapToShort);
				boolA[i] = bool;
			}
			yield new Argument(boolA);
		}
		case DoubleArray -> {
			if((!str.startsWith("[") || !str.endsWith("]")))
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name
						+ "\"  Double array has to start with \'[\' and end with \']\', insted got: \"" + str + "\".", shortNameMap, nameMapToShort);

			final String[] strA = getArrayFromString(str);
			final double[] doubleA = new double[strA.length];
			for (int i = 0; i < strA.length; i++) {
				final Double doubl = resolveDouble(strA[i]);
				if(doubl == null)
					HelpMessage.print(
							"Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected type double, insted got: \" [ ... " + strA[i] + " ... ]\".",
							shortNameMap, nameMapToShort);
				doubleA[i] = doubl;
			}
			yield new Argument(doubleA);
		}
		case IntArray -> {
			if((!str.startsWith("[") || !str.endsWith("]")))
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name
						+ "\"  Int array has to start with \'[\' and end with \']\', insted got: \"" + str + "\".", shortNameMap, nameMapToShort);

			final String[] strA = getArrayFromString(str);
			final int[] intA = new int[strA.length];
			for (int i = 0; i < strA.length; i++) {
				final Integer in = resolveInt(strA[i]);
				if(in == null)
					HelpMessage.print(
							"Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected type int, insted got: \" [ ... " + strA[i] + " ... ]\".",
							shortNameMap, nameMapToShort);
				intA[i] = in;
			}
			yield new Argument(intA);
		}
		case None -> {
			if(!str.equals(""))
				HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name + "\"  Expected no arguments, insted got: \"" + str + "\".", shortNameMap,
						nameMapToShort);
			yield new Argument();
		}
		};

		if(argTypeExpected == ArgType.None && arg.getArgType() != ArgType.None)
			HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name + "\" cannot have any arguments.", shortNameMap, nameMapToShort);

		if(argTypeExpected != ArgType.None && arg.getArgType() == ArgType.None)
			HelpMessage.print("Argument: \"" + (isShort ? "-" : "--") + name + "\" has to have an argument type: \""
					+ HelpMessage.getArgTypeString(argTypeExpected) + "\".", shortNameMap, nameMapToShort);

		argumentMap.put(newName, arg);
	}

	private static String[] getArrayFromString(String str) {
		str = str.substring(1, str.length() - 1);
		boolean isQuote = false;
		int bracket = 0;
		int prevIndex = 0;
		final List<String> list = new ArrayList<>();
		final char[] charA = str.toCharArray();
		for (int i = 0; i < charA.length; i++) {
			final char c = charA[i];
			final char p = i == 0 ? '?' : charA[i - 1];
			switch (c) {
			case ',' -> {
				if(!isQuote && bracket == 0) {
					list.add(str.substring(prevIndex, i).strip());
					prevIndex = i + 1;
				}
			}
			case '\"' -> {
				if(p != '\\')
					isQuote = !isQuote;
			}
			case '[' -> bracket++;
			case ']' -> bracket--;
			}
		}
		list.add(str.substring(prevIndex).strip());

		return list.toArray(String[]::new);
	}

	private Boolean resolveBoolean(final String str) {
		final String lower = str.toLowerCase().strip();
		if(lower.equals("true") || lower.equals("1"))
			return true;
		if(lower.equals("false") || lower.equals("0"))
			return false;
		return null;
	}

	private Integer resolveInt(final String str) {
		try {
			return Integer.valueOf(str);
		} catch (final Exception e) {
			return null;
		}
	}

	private Double resolveDouble(final String str) {
		try {
			return Double.valueOf(str);
		} catch (final Exception e) {
			return null;
		}
	}

	private String resolveString(final String str) {
		if(str.startsWith("\"") && str.endsWith("\""))
			return str.substring(1, str.length() - 1);
		return str;
	}
}
