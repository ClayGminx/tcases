package org.cornutum.tcases.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 减少用例的指令
 */
@Getter
@Setter
public class LessenCommand {
    private LessenCommandName commandName;
    private String varName;
    private String varType;
    private Object varValue;
    private String kind;

    public LessenCommand(LessenCommandName commandName, String varName) {
        this.commandName = commandName;
        this.varName = varName;
    }
}
