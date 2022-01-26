package me.krypek.freeargparser;

import java.util.Arrays;

//@f:off
class Examples {

	public static void main(String[] args) { fullTest(); }

	public static void fullTest() {

		final String input = """
			-i 1 \
			-d=2.3 \
			-s hgfgf \
			-b=true \
			-na \
			--intArray=[1, 2, 3, 4, 5] \
			-strA [hello guys, "hi bro", yooooo] \
			-douA=[2.1, 1.3, 3.7, 7] \
			-booA [0, 0, 0, 1, false, true] """;

		ParsedData data = new ParserBuilder()
				.add("i", 	"int", 			true,	false, 	ArgType.Int, 			"your_description1")
				.add("d", 	"double", 		true,	true,  ArgType.Double,			"your_description2")
				.add("s", 	"string", 		true,	false,  ArgType.String,			"your_description3")
				.add("b", 	"boolean", 		true,	true,  ArgType.Boolean,			"your_description4")
				.add("na", 	"noarg",		false,	false, 	ArgType.None, 			"your_description5")
				.add("intA","intArray", 	false,	true,  ArgType.IntArray, 		"your_description6")
				.add("strA","stringArray", 	true,	false,  ArgType.StringArray, 	"your_description7")
				.add("douA","doubleArray", 	true,	true,  ArgType.DoubleArray, 	"your_description8")
				.add("booA","booleanArray",	true,	false,  ArgType.BooleanArray,	"your_description9")
				.parse(input);


		System.out.println("\nint:\t\t"+data.getIntOrDef("i", 1)+
						   "\ndouble:\t\t"+data.getDouble("d")+
						   "\nstring:\t\t"+data.getString("s")+
						   "\nboolean:\t"+data.getBoolean("b")+
						   "\nnoarg:\t\t"+data.has("na")+
						   "\nintA:\t\t"+Arrays.toString(data.getIntArray("intA"))+
						   "\nstrA:\t\t"+Arrays.toString(data.getStringArray("strA"))+
						   "\ndouA:\t\t"+Arrays.toString(data.getDoubleArray("douA"))+
						   "\nbooA:\t\t"+Arrays.toString(data.getBooleanArray("booA"))
				);

	}
	
	@SuppressWarnings("unused")
	public static void simple1(String[] args) {
		ParsedData data = new ParserBuilder()
				.add("i", 	"int", 			true,	false, 	ArgType.Int, 		"integer")
				.add("d", 	"double", 		true,	false,  ArgType.Double,		"double")
				.parse(args);
		
		int i = data.getInt("i");
		double d = data.getDouble("double");
	}

}
//@f:on
