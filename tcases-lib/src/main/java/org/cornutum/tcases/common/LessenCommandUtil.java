package org.cornutum.tcases.common;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class LessenCommandUtil {

    public static List<LessenCommand> read(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Scanner scanner = new Scanner(fis)) {

            List<LessenCommand> list = new LinkedList<>();
            int i = 0;
            while (scanner.hasNextLine()) {
                String cmdLine = scanner.nextLine();
                logger.debug("处理第{}行：{}", ++i, cmdLine);

                String[] cmdParts = cmdLine.split(" ");
                String cmdName = cmdParts[0];

                if (LessenCommandName.ONLY.toString().equals(cmdName) || LessenCommandName.EXCLUDE.toString().equals(cmdName)) {
                    String[] nameParts = cmdParts[1].split("=");
                    if (nameParts.length == 2 && "name".equals(nameParts[0])) {
                        String varName = nameParts[1];
                        LessenCommand lessenCommand = new LessenCommand(LessenCommandName.valueOf(cmdName), varName);

                        for (int j = 2; j < cmdParts.length; j++) {
                            internalReadCommandLine(lessenCommand, cmdParts[j]);
                        }

                        list.add(lessenCommand);
                    }
                } else {
                    logger.warn("第{}行指令名{}不被支持！", i, cmdName);
                }
            }
            return list;

        } catch (IOException e) {
            throw new RuntimeException("无法读取减少用例的指令文件", e);
        }
    }

    private static void internalReadCommandLine(LessenCommand lessenCommand, String commandPart) {
        String[] parts = commandPart.split("=");
        switch (parts[0]) {
            case "value":
                lessenCommand.setVarValue(parts[1]);
                break;
            case "type":
                lessenCommand.setVarType(parts[1]);
                break;
            case "kind":
                lessenCommand.setKind(parts[1]);
                break;
            default:
                logger.warn("{}不被支持！", parts[0]);
                break;
        }
    }
}
