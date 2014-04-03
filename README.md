type-mapper-for-uima
====================

This is a generic tool for the UIMA framework (http://uima.apache.org/ ) that converts one set of UIMA types into another.

The type conversion rules are described in a config file.

The config file is passed to TypeMapper as a parameter "config-file-name".
See TypeMapperTest.java as an example.


Here is the expected format of the config file:
<rules>
	<rule>
		<input>
			your input type
		</input>
		<output>
			your output type
		</output>
	</rule>
	<rule>
		...
	</rule>
	...
</rules>
