package me.krypek.freeargparser;

import java.util.LinkedHashMap;

class BuilderArg {
	public final ArgType argType;
	public final boolean isRequired;
	public final String description;

	public BuilderArg(final ArgType argType, final boolean isRequired, final String description) {
		this.argType = argType;
		this.isRequired = isRequired;
		this.description = description;
	}
}

public class ParserBuilder {

	LinkedHashMap<String, BuilderArg> shortNameMap;
	LinkedHashMap<String, String> nameMapToShort;

	public ParserBuilder() {
		shortNameMap = new LinkedHashMap<>();
		nameMapToShort = new LinkedHashMap<>();
	}

	public ParserBuilder add(final String shortName, final String longName, final boolean isRequired, final ArgType argType, final String description) {
		if(shortNameMap.containsKey(shortName))
			throw new FreeArgParserException("Cannot add short name: \"" + shortName + "\" twice.");
		if(nameMapToShort.containsKey(longName))
			throw new FreeArgParserException("Cannot add long name: \"" + longName + "\" twice.");
		shortNameMap.put(shortName, new BuilderArg(argType, isRequired, description.stripIndent()));
		nameMapToShort.put(longName, shortName);
		return this;
	}

	public FreeArgParser build() { return new FreeArgParser(shortNameMap, nameMapToShort); }

	public ParsedData parse(final String str) { return new FreeArgParser(shortNameMap, nameMapToShort).parse(str); }

	public ParsedData parse(final String[] arr) { return new FreeArgParser(shortNameMap, nameMapToShort).parse(arr); }
}
