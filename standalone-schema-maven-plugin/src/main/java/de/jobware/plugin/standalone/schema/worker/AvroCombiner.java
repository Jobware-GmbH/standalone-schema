package de.jobware.plugin.standalone.schema.worker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.jobware.plugin.standalone.schema.loader.AvroJsonLoader;
import de.jobware.plugin.standalone.schema.util.AvroUtil;

import java.io.FileNotFoundException;

public class AvroCombiner {

    private final AvroJsonLoader avroSchemaLoader;

    public AvroCombiner(AvroJsonLoader avroSchemaLoader) {
        this.avroSchemaLoader = avroSchemaLoader;
    }

    public void combineSchema(JsonObject jsonSchema) throws FileNotFoundException {
        JsonElement type = jsonSchema.get("type");

        if (type.isJsonPrimitive()) {
            String typeName = type.getAsString();
            if (AvroUtil.PRIMITIVES.contains(typeName)) {
                return;
            }
            if (typeName.equals("record")) {
                combineRecord(jsonSchema);
            }
            if (typeName.equals("array")) {
                combineCollection(jsonSchema, "items");
            }
            if (typeName.equals("map")) {
                combineCollection(jsonSchema, "values");
            }
            if (!AvroUtil.AVRO_TYPES.contains(typeName)) {
                JsonElement custom = avroSchemaLoader.loadJsonByName(typeName);
                combineSchema(custom.getAsJsonObject());
                jsonSchema.add("type", custom);
            }
        }
        else if (type.isJsonObject()) {
            combineSchema(type.getAsJsonObject());
        }
        else if (type.isJsonArray()) {
            combineUnion(jsonSchema);
        }
        else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    /**
     * Unions are represented using JSON arrays. <br/>
     * For example, ["null", "string"] declares a schema which may be either a null or string.
     * */
    private void combineUnion(JsonObject union) throws FileNotFoundException {
        JsonArray typesArray = union.get("type").getAsJsonArray();
        for (int i = 0; i < typesArray.size(); i++) {
            JsonElement type = typesArray.get(i);
            if (type.isJsonObject()) {
                combineSchema(type.getAsJsonObject());
            }
            else {
                String typeName = type.getAsString();
                if (!AvroUtil.PRIMITIVES.contains(typeName)) {
                    JsonElement t = avroSchemaLoader.loadJsonByName(typeName);
                    combineSchema(t.getAsJsonObject());
                    typesArray.set(i, t);
                }
            }
        }
    }

    /**
     * When combining a record, every field must be inline-ly defined
     * */
    private void combineRecord(JsonObject record) throws FileNotFoundException {
        JsonArray fields = record.getAsJsonArray("fields");
        for (JsonElement field : fields) {
            combineSchema(field.getAsJsonObject());
        }
    }

    /**
     * Collections are 'map' and 'array'
     * @param collection - JsonObject of a map or an array
     * @param items - 'items' for an array or 'values' for a map
     * */
    private void combineCollection(JsonObject collection, String items) throws FileNotFoundException {
        JsonElement itemsType = collection.get(items);
        if (itemsType.isJsonObject()) {
            combineSchema(itemsType.getAsJsonObject());
            return;
        }
        String typeName = itemsType.getAsString();
        if (!AvroUtil.PRIMITIVES.contains(typeName)) {
            JsonElement t = avroSchemaLoader.loadJsonByName(typeName);
            combineSchema(t.getAsJsonObject());
            collection.add(items, t);
        }
    }
}
