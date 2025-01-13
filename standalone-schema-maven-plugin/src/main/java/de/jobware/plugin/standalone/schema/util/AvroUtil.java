package de.jobware.plugin.standalone.schema.util;

import java.util.HashSet;
import java.util.Set;

public class AvroUtil {

    public static final String AVRO_SCHEMA_EXTENSION = ".avsc";

    public static final Set<String> AVRO_TYPES = new HashSet<>();

    static {
        AVRO_TYPES.add("string");
        AVRO_TYPES.add("bytes");
        AVRO_TYPES.add("int");
        AVRO_TYPES.add("long");
        AVRO_TYPES.add("float");
        AVRO_TYPES.add("double");
        AVRO_TYPES.add("boolean");
        AVRO_TYPES.add("null");

        AVRO_TYPES.add("record");
        AVRO_TYPES.add("enum");
        AVRO_TYPES.add("array");
        AVRO_TYPES.add("map");
        AVRO_TYPES.add("fixed");
    }

    public static final Set<String> PRIMITIVES = new HashSet<>();

    static {
        PRIMITIVES.add("string");
        PRIMITIVES.add("bytes");
        PRIMITIVES.add("int");
        PRIMITIVES.add("long");
        PRIMITIVES.add("float");
        PRIMITIVES.add("double");
        PRIMITIVES.add("boolean");
        PRIMITIVES.add("null");
    }

}
