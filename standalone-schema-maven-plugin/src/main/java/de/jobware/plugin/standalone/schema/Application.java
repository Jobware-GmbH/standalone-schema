package de.jobware.plugin.standalone.schema;

import com.google.gson.JsonObject;
import de.jobware.plugin.standalone.schema.loader.AvroJsonLoader;
import de.jobware.plugin.standalone.schema.worker.AvroCombiner;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;

@Mojo(name = "standalone-schema", defaultPhase = LifecyclePhase.VALIDATE)
public class Application extends AbstractMojo {

    @Parameter(property = "schemas", required = true)
    private String[] schemas;

    @Parameter(property = "sourceDirectory",
            defaultValue = "${project.basedir}/src/main/avro/")
    private String sourceDirectory;

    @Parameter(property = "outputDirectory",
            defaultValue = "${project.build.directory}/generated-main-standalone-avro/")
    private String outputDirectory;

    public void execute() throws MojoExecutionException {
        AvroJsonLoader ajLoader = new AvroJsonLoader(sourceDirectory, outputDirectory);
        AvroCombiner avroCombiner = new AvroCombiner(ajLoader);

        for(String schemaName : schemas) {
            try {
                JsonObject jsonElement  = ajLoader.loadJsonByName(schemaName);
                avroCombiner.combineSchema(jsonElement);
                ajLoader.streamAvroSchema(jsonElement);
            } catch (IOException e) {
                getLog().error("Error processing schema: " + e.getMessage());
                throw new MojoExecutionException("Unable to create the standalone schema", e);
            }
        }
    }
}
