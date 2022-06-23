

<h2>FreeArgParser</h2>
FreeArgParser library provides API for parsing command line arguments passed to programs written in Java.<br>
Basically <a href="https://commons.apache.org/proper/commons-cli/">Commons CLI</a> but with array support, cleaner syntax and with GNU GPLv3 license.
 
<h4>Example help message on parsing error:</h4>

```
 Short Name  Long Name       Argument Type  Is Required  Description
  -i          --int           int            +            your_description1
  -d          --double        double         +            your_description2
  -s          --string        String                      your_description3
  -b          --boolean       boolean        +            your_description4
  -na         --noarg         no argument                 your_description5
  -intA       --intArray      int[]          +            your_description6
  -strA       --stringArray   String[]       +            your_description7
  -douA       --doubleArray   double[]                    your_description8
  -booA       --booleanArray  boolean[]                   your_description9
```

<h2>Implementation</h2>
After importing the .jar, classes used can be found in me.krypek.freeargparser<br>
Here's a simple two argument program:

```java
ParsedData data = new ParserBuilder()
   .add("i", 	"int",	  true,	false, 	ArgType.Int,	"integer")
   .add("d", 	"double", true,	false,  ArgType.Double, "double")
   .parse(args);
		
int i = data.getInt("i");
double d = data.getDouble("double");
 ```
 <h3>Explaining</h3>
Firstable, you have to create new ParserBuilder instance.<br>
Then, you add all arguments you want, by calling the add function.<br>
Example:

```java
.add("a",        "argument", true,        false,       ArgType.Int,   "description")
//   short name, long name,  is_required, = style, argument type, description
```
<br>You can access the argument either by:<ul>
<li>Short name, e.g. -a</li>
<li>Long name, e.g. --argument</li></ul>

<br>When is_required is set to true, the argument has to be provided.<br>If it's not, the program will display the help message with the error and terminate.<br><br>
If = style is set to true, value will have to be connected with argument name using = char, not space char.<br>
Example: 
```
--testArg=2
```
insted of
```
--testArg 2
```
<br>

<br>
Description is what will be displayed in help message about the argument.<br><br>

Argument types:<br>
| ArgType 				| Usage 				| Comments 		|
| :---         			|          :---: 		|    :---:      |
| <i>None</i>   		|     					| no arguments	|		
| <i>Int</i>     		| 123			 		|				|
| <i>Double</i>     	| 3.14159		 		|				|
| <i>Boolean</i>     	| true, false, 1, 0		| 1=true, 0=false|
| <i>String</i>     	| pancake, "pan cake"	| "pancake" == pancake|
| <i>IntArray</i>     	| [1, 2, 3, 4]			| 				|
| <i>DoubleArray</i>    | [3.141, 0.25, 0.5]	|				|
| <i>BooleanArray</i>   | [false, 0, true, 1, 0]| 1=true, 0=false|
| <i>StringArray</i>    | [hi, "hell o", spa ce]| "hell o" == hell o|


<br>
If you want to put " (quote char) in your string, type \ before it.<br><br>

	
After adding all of your wanted arguments, parse them.<br>
Parse method accepts both String and String[].
```java
.parse(args);
```
Alternativly, you can build it and then parse it, both methods do the same.
```java
.build().parse(args);
```
<h3>Accessing data</h3>
After you have sucessfully parsed your arguments into ParsedData class, you can access the data using it's short name.
Example for getting int:

```java
int i = parseddata.getInt("i");
```
And same for other data types.<br>
If the argument isn't required and wasn't provided by the user, FreeArgParser will throw an exception.<br>
To avoid that, use

```java
int i = parseddata.getIntOrDef("i", 1);
```
<br>
If an argument has ArgType set to None, you need to

```java
boolean doesExist = parseddata.has("na");
```
in order to check if it was provided.<br><br><br>

Here's an example program utilizing all argument types:
```java
final String input = """
	-i 1\
	-d=2.3 \
	-s hgfgf \
	-b=true \
	-na \
	--intArray=[1, 2, 3, 4, 5] \
	-strA [hello guys, "hi bro", yooooo] \
	-douA=[2.1, 1.3, 3.7, 7] \
	-booA [0, 0, 0, 1, false, true] """;

ParsedData data = new ParserBuilder()
	.add("i", 	"int", 		true,	false, 	ArgType.Int, 		"description1")
	.add("d", 	"double", 	true,	true,  	ArgType.Double,		"description2")
	.add("s", 	"string", 	true,	false,  ArgType.String,		"description3")
	.add("b", 	"boolean", 	true,	true,  	ArgType.Boolean,	"description4")
	.add("na", 	"noarg",	false,	false, 	ArgType.None, 		"description5")
	.add("intA",	"intArray", 	false,	true,  	ArgType.IntArray, 	"description6")
	.add("strA",	"stringArray", 	true,	false,  ArgType.StringArray, 	"description7")
	.add("douA",	"doubleArray", 	true,	true,  	ArgType.DoubleArray, 	"description8")
	.add("booA",	"booleanArray",	true,	false,  ArgType.BooleanArray,	"description9")
	.parse(input);


System.out.println("\nint:\t\t"+data.getIntOrDef("i", 1)+
	"\ndouble:\t\t"+data.getDouble("d")+
	"\nstring:\t\t"+data.getString("s")+
	"\nboolean:\t"+data.getBoolean("b")+
	"\nnoarg:\t\t"+data.has("na")+
	"\nintA:\t\t"+Arrays.toString(data.getIntArray("intA"))+
	"\nstrA:\t\t"+Arrays.toString(data.getStringArray("strA"))+
	"\ndouA:\t\t"+Arrays.toString(data.getDoubleArray("douA"))+
	"\nbooA:\t\t"+Arrays.toString(data.getBooleanArray("booA"))  );
```
Output:
```
int:		1
double:		2.3
string:		hgfgf
boolean:	true
noarg:		true
intA:		[1, 2, 3, 4, 5]
strA:		[hello guys, hi bro, yooooo]
douA:		[2.1, 1.3, 3.7, 7.0]
booA:		[false, false, false, true, false, true]
```
<br><br><br>
