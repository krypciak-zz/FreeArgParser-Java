package me.krypek.freeargparser;

import java.util.HashMap;

public class ParsedData {

	private final HashMap<String, Argument> shortNameMap;

	public ParsedData(final HashMap<String, Argument> map) { shortNameMap = map; }

	public Argument getArgument(final String shortName) {
		final Argument arg = shortNameMap.get(shortName);
		if(arg == null)
			throw new FreeArgParserException("Value: \"" + shortName + "\" does not exist.");
		return arg;
	}

	public Argument getArgumentForce(final String shortName) { return shortNameMap.get(shortName); }

	public boolean has(final String shortName) { return shortNameMap.containsKey(shortName); }

	public int getInt(final String shortName) { return getArgument(shortName).getInt(); }

	public double getDouble(final String shortName) { return getArgument(shortName).getDouble(); }

	public boolean getBoolean(final String shortName) { return getArgument(shortName).getBoolean(); }

	public String getString(final String shortName) { return getArgument(shortName).getString(); }

	public String[] getStringArray(final String shortName) { return getArgument(shortName).getStringArray(); }

	public int[] getIntArray(final String shortName) { return getArgument(shortName).getIntArray(); }

	public double[] getDoubleArray(final String shortName) { return getArgument(shortName).getDoubleArray(); }

	public boolean[] getBooleanArray(final String shortName) { return getArgument(shortName).getBooleanArray(); }

	// get or def

	public int getIntOrDef(final String shortName, final int defaultInt) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultInt;
		return arg.getInt();
	}

	public double getDoubleOrDef(final String shortName, final double defaultDouble) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultDouble;
		return arg.getDouble();
	}

	public Boolean getBooleanOrNull(final String shortName) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return null;
		return arg.getBoolean();
	}

	public String getStringOrDef(final String shortName, final String defaultString) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultString;
		return arg.getString();
	}

	public String[] getStringArrayOrDef(final String shortName, final String[] defaultArray) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultArray;
		return arg.getStringArray();
	}

	public int[] getIntArrayOrDef(final String shortName, final int[] defaultArray) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultArray;
		return arg.getIntArray();
	}

	public double[] getDoubleArrayOrDef(final String shortName, final double[] defaultArray) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultArray;
		return arg.getDoubleArray();
	}

	public boolean[] getBooleanArrayOrDef(final String shortName, final boolean[] defaultArray) {
		Argument arg = getArgumentForce(shortName);
		if(arg == null)
			return defaultArray;
		return arg.getBooleanArray();
	}

	@Override
	public String toString() { return shortNameMap.toString(); }

}
