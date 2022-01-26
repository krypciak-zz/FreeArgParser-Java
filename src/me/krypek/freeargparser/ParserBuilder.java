package me.krypek.freeargparser;

import java.util.LinkedHashMap;

class BuilderArg {
	public final ArgType argType;
	public final boolean isRequired;
	public final String description;
	public final boolean equalSign;

	public BuilderArg(final ArgType argType, final boolean isRequired, final String description, final boolean equalSign) {
		this.argType = argType;
		this.isRequired = isRequired;
		this.description = description;
		this.equalSign = equalSign;
	}
}

public class ParserBuilder {

	LinkedHashMap<String, BuilderArg> shortNameMap;
	LinkedHashMap<String, String> nameMapToShort;

	public ParserBuilder() {
		shortNameMap = new LinkedHashMap<>();
		nameMapToShort = new LinkedHashMap<>();
	}

	/**
	 * @author krypek
	 * @see <a href="https://github.com/krypciak/FreeArgParser-Java">Project's
	 *      Github Page</a>
	 * 
	 * 
	 * @param shortName   Argument's short name, -?
	 * @param longName    Argument's long name, --?
	 * @param isRequired  If true, program will terminate with help message if the
	 *                    argument wasn't provided.
	 * @param equalSign   If true, argument connection with value will be '=' and
	 *                    not ' '. <br>
	 *                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Example
	 *                    if true: -a=1<br>
	 *                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Example
	 *                    if false: -a 1
	 * @param argType     Specify the argument type
	 * @param description What will be displayed in help message about the argument.
	 * @return
	 */

	public ParserBuilder add(final String shortName, final String longName, final boolean isRequired, final boolean equalSign, final ArgType argType, final String description) {
		if(shortNameMap.containsKey(shortName))
			throw new FreeArgParserException("Cannot add short name: \"" + shortName + "\" twice.");
		if(nameMapToShort.containsKey(longName))
			throw new FreeArgParserException("Cannot add long name: \"" + longName + "\" twice.");
		shortNameMap.put(shortName, new BuilderArg(argType, isRequired, description.stripIndent(), equalSign));
		nameMapToShort.put(longName, shortName);
		return this;
	}

	public FreeArgParser build() { return new FreeArgParser(shortNameMap, nameMapToShort); }

	/**
	 * @author krypek
	 * @see <a href="https://github.com/krypciak/FreeArgParser-Java">Project's
	 *      Github Page</a>
	 * 
	 * @param str String to be parsed
	 * @return ParsedData
	 */

	public ParsedData parse(final String str) { return new FreeArgParser(shortNameMap, nameMapToShort).parse(str); }

	/**
	 * @author krypek
	 * @see <a href="https://github.com/krypciak/FreeArgParser-Java">Project's
	 *      Github Page</a>
	 * 
	 * @param arr String array to be parsed
	 * @return ParsedData
	 */
	public ParsedData parse(final String[] arr) { return new FreeArgParser(shortNameMap, nameMapToShort).parse(arr); }
}
