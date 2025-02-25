package org.cornutum.tcases.generator.less;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LessenRulesParser {

    public static LessenRules parse(File file) {
        Map<String, Object> map;
        try (InputStream is = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            map = yaml.load(is);
        } catch (IOException e) {
            throw new RuntimeException("解析失败！", e);
        }

        LessenRules rules = new LessenRules();

        String value = (String) map.get("必填校验");
        rules.setCheckDefined(toBoolean(value));

        value = (String) map.get("字段长度校验");
        rules.setCheckLength(toBoolean(value));

        value = (String) map.get("字段类型校验");
        rules.setCheckType(toBoolean(value));

        Map<?, ?> valueDomain = (Map<?, ?>) map.get("值域");
        if (valueDomain != null) {
            value = (String) valueDomain.get("有效等价类");
            rules.setUseValidTuples(toBoolean(value));

            value = (String) valueDomain.get("无效等价类");
            rules.setUseFailureTuples(toBoolean(value));
        } else {
            rules.setUseValidTuples(true);
            rules.setUseFailureTuples(true);
        }

        return rules;
    }

    private static boolean toBoolean(String arg) {
        if (arg == null) {
            return true;
        }
        switch (arg) {
            case "":
            case "是": return true;
            case "否": return false;
            default: throw new IllegalArgumentException("枚举值非法！合法值应为是或否。");
        }
    }
}
