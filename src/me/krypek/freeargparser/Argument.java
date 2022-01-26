package me.krypek.freeargparser;

import java.util.Arrays;

/**
 * Class that stores data about single argument.<br>
 * 
 * 
 * @author krypek
 * @see <a href="https://github.com/krypciak/FreeArgParser-Java">Project's
 *      Github Page</a>
 */

public class Argument {
	private final int INT_DEFAULT = Integer.MIN_VALUE;
	private final double DOUBLE_DEFAULT = Double.MIN_VALUE;
	private final boolean BOOLEAN_DEFAULT = false;
	private final String STRING_DEFAULT = null;
	private final String[] STRING_ARRAY_DEFAULT = null;
	private final int[] INT_ARRAY_DEFAULT = null;
	private final double[] DOUBLE_ARRAY_DEFAULT = null;
	private final boolean[] BOOLEAN_ARRAY_DEFAULT = null;

	private final ArgType argType;
	private final int intValue;
	private final double doubleValue;
	private final boolean booleanValue;
	private final String stringValue;
	private final String[] stringArrayValue;
	private final int[] intArrayValue;
	private final double[] doubleArrayValue;
	private final boolean[] booleanArrayValue;

	@Override
	public String toString() {
		return switch (argType) {
		case Int -> intValue + "i";
		case Boolean -> booleanValue + "";
		case Double -> doubleValue + "d";
		case String -> "\"" + stringValue + "\"";
		case StringArray -> Arrays.toString(stringArrayValue);
		case None -> "none";
		case BooleanArray -> Arrays.toString(booleanArrayValue);
		case DoubleArray -> Arrays.toString(doubleArrayValue);
		case IntArray -> Arrays.toString(intArrayValue);
		};
	}

	public Argument() {
		argType = ArgType.None;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final int value) {
		argType = ArgType.Int;
		intValue = value;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final double value) {
		argType = ArgType.Double;
		intValue = INT_DEFAULT;
		doubleValue = value;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final boolean value) {
		argType = ArgType.Boolean;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = value;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final String value) {
		argType = ArgType.String;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = value;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final String[] value) {
		argType = ArgType.StringArray;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = value;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final int[] value) {
		argType = ArgType.IntArray;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = value;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final double[] value) {
		argType = ArgType.DoubleArray;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = value;
		booleanArrayValue = BOOLEAN_ARRAY_DEFAULT;
	}

	public Argument(final boolean[] value) {
		argType = ArgType.BooleanArray;
		intValue = INT_DEFAULT;
		doubleValue = DOUBLE_DEFAULT;
		booleanValue = BOOLEAN_DEFAULT;
		stringValue = STRING_DEFAULT;
		stringArrayValue = STRING_ARRAY_DEFAULT;
		intArrayValue = INT_ARRAY_DEFAULT;
		doubleArrayValue = DOUBLE_ARRAY_DEFAULT;
		booleanArrayValue = value;
	}

	ArgType getArgType() { return argType; }

	public double getDouble() {
		if(argType == ArgType.Double)
			return doubleValue;
		throw new FreeArgParserException("Cannot access value \"double\" when argument type is \"" + argType.toString() + "\".");
	}

	public int getInt() {
		if(argType == ArgType.Int)
			return intValue;
		throw new FreeArgParserException("Cannot access value \"int\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

	public boolean getBoolean() {
		if(argType == ArgType.Boolean)
			return booleanValue;
		throw new FreeArgParserException("Cannot access value \"boolean\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

	public String getString() {
		if(argType == ArgType.String)
			return stringValue;
		throw new FreeArgParserException("Cannot access value \"String\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

	public String[] getStringArray() {
		if(argType == ArgType.StringArray)
			return stringArrayValue;
		throw new FreeArgParserException("Cannot access value \"String[]\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

	public int[] getIntArray() {
		if(argType == ArgType.IntArray)
			return intArrayValue;
		throw new FreeArgParserException("Cannot access value \"int[]\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

	public double[] getDoubleArray() {
		if(argType == ArgType.DoubleArray)
			return doubleArrayValue;
		throw new FreeArgParserException("Cannot access value \"double[]\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

	public boolean[] getBooleanArray() {
		if(argType == ArgType.BooleanArray)
			return booleanArrayValue;
		throw new FreeArgParserException("Cannot access value \"boolean[]\" when argument type is \"" + HelpMessage.getArgTypeString(argType) + "\".");
	}

}
