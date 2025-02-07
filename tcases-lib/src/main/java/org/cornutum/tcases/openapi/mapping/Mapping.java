package org.cornutum.tcases.openapi.mapping;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

    private Map<String, FieldMapping> fieldMappings = new HashMap<>();

    public void addFieldMapping(String fieldName, FieldMapping fieldMapping) {
        fieldMappings.put(fieldName, fieldMapping);
    }

    public FieldMapping getFieldMapping(String fieldName) {
        return fieldMappings.get(fieldName);
    }

    public Map<String, FieldMapping> getFieldMappings() {
        return fieldMappings;
    }
}
