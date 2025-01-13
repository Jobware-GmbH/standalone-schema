
# Standalone Avro Schema
### Generate standalone avro schemas

### usage 
```xml
<project xmlns="example">
    <build>
        <plugins>
            <plugin>
                <groupId>de.jobware.plugin</groupId>
                <artifactId>standalone-schema-maven-plugin</artifactId>
                <version>${standalone-schema.version}</version>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-main-standalone-avro/
                    </outputDirectory>
                    <sourceDirectory>
                        ${project.basedir}/src/main/avro/
                    </sourceDirectory>
                    <schemas>
                        <schema>com.example.avro.model.Person</schema>
                        <schema>com.example.avro.model.Address</schema>
                    </schemas>
                </configuration>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>standalone-schema</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
### Configuration: 

`sourceDirectory` - Source directory with the avro files. Default value: `${project.basedir}/src/main/avro/`

`outputDirectory` - Output directory wehre the processed schema is stored. Default value: `${project.build.directory}/generated-main-standalone-avro/`

`schemas` - Names of the schemas to combine into standalone avro schema.