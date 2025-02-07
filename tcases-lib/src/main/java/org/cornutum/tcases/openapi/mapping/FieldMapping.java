package org.cornutum.tcases.openapi.mapping;

public class FieldMapping {

    private final String tableName;
    private final String tableFieldName;
    private final String modelName;
    private final String modelFieldName;

    public FieldMapping(String table, String model) {
        if (table == null || table.isEmpty()) {
            throw new IllegalArgumentException("Missing table!");
        }
        if (model == null || model.isEmpty()) {
            throw new IllegalArgumentException("Missing model!");
        }

        String[] items;
        items = internalSplit(table, "table");
        tableName = items[0];
        tableFieldName = items[1];
        items = internalSplit(model, "model");
        modelName = items[0];
        modelFieldName = items[1];
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelFieldName() {
        return modelFieldName;
    }

    private static String[] internalSplit(String arg, String name) {
        if (arg.contains(".")) {
            String[] parts = arg.split("[.]");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid " + name + ": " + arg);
            }
            return parts;
        } else {
            throw new IllegalArgumentException("Invalid " + name + ": " + arg);
        }
    }
}
