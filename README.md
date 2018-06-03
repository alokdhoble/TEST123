# IDENTITY-E2E-TEST

1. Source code build under java build 1.8.0_171-b11.
2. Copy fileproperty.jar, log4j.properties & input.properties file in one location.
3. Update input.properties files for following properties
	i. propFileLocation - Give fully qualified log4j.properties file location.
       ii. source.directory - give source file directory. ( This folder is required if fileproperty.jar is running standalocne. If it is called by another source code then value for this property is non-mandatory.)
4. Execute standalone 
	1. cd <<source folder where jar, log4j.properties, input.properties available>>
	2. java -jar fileproperty.jar
