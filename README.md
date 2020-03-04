# Parser Example #

[Maven](https://maven.apache.org/what-is-maven.html) is used as a build tool for Java.
Maven handles dependencies, compiling code, running code, and running the test suite.
[JaCoCo](https://www.eclemma.org/jacoco/) is used to measure the [code coverage](https://en.wikipedia.org/wiki/Code_coverage) of the test suite, which gives a sense of how good the tests are.
The `pom.xml` file in this directory holds configuration information for Maven.

The code can be compiled with:

```console
mvn compile
```

The test suite can be run with:

```console
mvn test
```

## Abstract Grammar ##

```
x is a variable
i is an integer
e is an expression

e ::= x | i | if (e_1) e_2 else e_3 | e_1 + e_2 | ( e )
```

## Concrete Grammar ##

```
x is a variable
i is an integer
e is an expression
a is an additive expression
p is a primary expression

e ::= if (e_1) e_2 else e_3 | a
a ::= p (+ a)*
p ::= x | i | ( e )
```
