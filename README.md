type-mapper-for-uima
====================

This is a generic tool for the UIMA framework (http://uima.apache.org/ ) that converts one set of UIMA types into another.

The type conversion rules are described in a config file.

At the moment the name of the config file is hardcoded in TypeMapper.java.
In order to use the tool create an XML file in the resourses: src/resources/com/radialpoint/typemapper/TypeMapperConfig.xml


Here is the expected format of the file:
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
