package org.cornutum.tcases.ext.business;

/**
 * 映射
 */
public class Mapping {

    private String field;
    private String type;
    private String targetLocation;
    private String targetName;

    public Mapping(String field, String type, String targetLocation, String targetName) {
        this.field = field;
        this.type = type;
        this.targetLocation = targetLocation;
        this.targetName = targetName;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getTargetName() {
        return targetName;
    }
}
