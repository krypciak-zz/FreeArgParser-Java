package me.krypek.freeargparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

interface CustomPrint {
	String print(String cause, HashMap<String, BuilderArg> shortNameMap, HashMap<String, String> longNameMapToShort);
}

class TablePrint implements CustomPrint {

	@Override
	public String print(final String cause, final HashMap<String, BuilderArg> shortNameMap, final HashMap<String, String> longNameMapToShort) {
		final StringBuilder sb = new StringBuilder(cause);
		sb.append("\nCorrect usage:");

		@SuppressWarnings("unchecked")
		final Entry<String, String>[] entrySet = longNameMapToShort.entrySet().toArray(Map.Entry[]::new);

		int nameShortWidth = -1;
		int nameLongWidth = -1;

		for (int i = 0; i < shortNameMap.size(); i++) {
			final int shortWidth = entrySet[i].getValue().length();
			if(shortWidth > nameShortWidth)
				nameShortWidth = shortWidth;
			final int longWidth = entrySet[i].getKey().length();
			if(longWidth > nameLongWidth)
				nameLongWidth = longWidth;
		}

		final int SHORT_LENGTH = "Short Name".length();
		final int LONG_LENGTH = "Long Name".length();
		final int ARGUMENT_TYPE_LENGTH = "Argument Type".length() + 2;
		final int IS_REQUIRED_LENGTH = "Is Required".length() + 1;

		if(nameShortWidth < SHORT_LENGTH)
			nameShortWidth = SHORT_LENGTH + 2;
		else
			nameShortWidth += 2;

		sb.append("\n Short Name");
		for (int i = 0; i < nameShortWidth - SHORT_LENGTH; i++)
			sb.append(" ");

		if(nameLongWidth < LONG_LENGTH)
			nameLongWidth = LONG_LENGTH + 2;
		else
			nameLongWidth += 2;

		sb.append("Long Name");
		for (int i = 0; i < nameLongWidth - LONG_LENGTH; i++)
			sb.append(" ");

		sb.append("  Argument Type  Is Required  Description\n");

		for (int i = 0; i < shortNameMap.size(); i++) {
			final String nameShort = entrySet[i].getValue();
			final String nameLong = entrySet[i].getKey();
			final BuilderArg arg = shortNameMap.get(nameShort);

			sb.append("  -");
			sb.append(nameShort);
			for (int x = 0; x < nameShortWidth - nameShort.length() - 1; x++)
				sb.append(" ");
			sb.append("--");
			sb.append(nameLong);
			for (int x = 0; x < nameLongWidth - nameLong.length(); x++)
				sb.append(" ");
			final String argTypeStr = HelpMessage.getArgTypeString(arg.argType);
			sb.append(argTypeStr);
			for (int x = 0; x < ARGUMENT_TYPE_LENGTH - argTypeStr.length(); x++)
				sb.append(" ");
			sb.append(arg.isRequired ? "+" : " ");
			for (int x = 0; x < IS_REQUIRED_LENGTH; x++)
				sb.append(" ");
			sb.append(arg.description);
			sb.append("\n");
		}

		return sb.toString();
	}

}

public class HelpMessage {

	public static CustomPrint printFunction = new TablePrint();

	public static void print(final String cause, final HashMap<String, BuilderArg> shortNameMap, final HashMap<String, String> longNameMapToShort) {
		System.out.println(printFunction.print(cause, shortNameMap, longNameMapToShort));
		quit();
	}

	public static String getArgTypeString(final ArgType argType) {
		return switch (argType) {
			case Boolean -> "boolean";
			case Double -> "double";
			case Int -> "int";
			case String -> "String";
			case StringArray -> "String[]";
			case None -> "no argument";
			case BooleanArray -> "boolean[]";
			case DoubleArray -> "double[]";
			case IntArray -> "int[]";
		};
	}

	private static void quit() { System.exit(-1); }
}
