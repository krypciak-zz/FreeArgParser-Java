package me.krypek.freeargparser;

import java.util.Arrays;

class Examples {

	public static void main(String[] args) { fullTest(); }

	public static void fullTest() {
		//@f:off
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

		FreeArgParser parser = new ParserBuilder()
				.add("i", 	"int", 			true,	false, 	ArgType.Int, 			"desc1")
				.add("d", 	"double", 		true,	true,  ArgType.Double,			"desc2")
				.add("s", 	"string", 		true,	false,  ArgType.String,			"desc3")
				.add("b", 	"boolean", 		true,	true,  ArgType.Boolean,			"bul bul bul")
				.add("na", 	"noarg",		false,	false, 	ArgType.None, 			"no arg test")
				.add("intA","intArray", 	false,	true,  ArgType.IntArray, 		" yo")
				.add("strA","stringArray", 	true,	false,  ArgType.StringArray, 	" yo")
				.add("douA","doubleArray", 	true,	true,  ArgType.DoubleArray, 	" yo")
				.add("booA","booleanArray",	true,	false,  ArgType.BooleanArray,	"yoo")
				.build();
		
		final ParsedData data = parser.parse(input);


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
	}

}
