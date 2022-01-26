<h2>FreeArgParser</h2>
FreeArgParser library provides API for parsing command line arguments passed to programs written in Java.
Basically <a href="https://commons.apache.org/proper/commons-cli/">Commons CLI</a> but with array support, cleaner syntax and with GNU GPLv3 license.
 
<h5>Example help message on parsing error:</h5>
```
 Short Name  Long Name       Argument Type  Is Required  Description
  -i          --int           int            +            desc1
  -d          --double        double         +            desc2
  -s          --string        String         +            desc3
  -b          --boolean       boolean        +            bul bul bul
  -na         --noarg         no argument                 no arg test
  -intA       --intArray      int[]                       yo
  -strA       --stringArray   String[]       +            yo
  -douA       --doubleArray   double[]       +            yo
  -booA       --booleanArray  boolean[]      +            yoo
```
