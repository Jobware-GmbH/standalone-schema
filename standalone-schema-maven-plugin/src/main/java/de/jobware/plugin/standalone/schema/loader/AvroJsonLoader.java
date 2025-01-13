package de.jobware.plugin.standalone.schema.loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.jobware.plugin.standalone.schema.util.AvroUtil;
import org.apache.avro.Schema;
import org.apache.avro.SchemaFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static de.jobware.plugin.standalone.schema.util.AvroUtil.AVRO_SCHEMA_EXTENSION;

public class AvroJsonLoader {

    private final String sourceDirectory;

    private final String outputDirectory;


    public AvroJsonLoader(String sourceDirectory, String outputDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.outputDirectory = outputDirectory;
    }

    public JsonObject loadJsonByName(String name) throws FileNotFoundException {
        String filePath = generateSchemaPath(name);
        return loadJsonFromFile(filePath);
    }

    public JsonObject loadJsonFromFile(String filePath) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(filePath));
        return new Gson().fromJson(reader, JsonObject.class);
    }

    public String generateSchemaPath(String schemaName) {
        String path = sourceDirectory;

        if(!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        if(schemaName.endsWith(AVRO_SCHEMA_EXTENSION)) {
            schemaName = schemaName.substring(0, schemaName.length() - AVRO_SCHEMA_EXTENSION.length());
        }
        schemaName = schemaName.replace(".", File.separator);
        path = path + schemaName + AVRO_SCHEMA_EXTENSION;
        return path;
    }

    public void streamAvroSchema(JsonObject jsonElement) throws IOException {
        final Schema finalSchema = new Schema.Parser().parse(jsonElement.toString());
        final String file = outputDirectory + File.separator + finalSchema.getName() + AvroUtil.AVRO_SCHEMA_EXTENSION;
        final Path path = Path.of(file);

        Files.createDirectories(path.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(SchemaFormatter.format("json", finalSchema));
        }
    }

}
