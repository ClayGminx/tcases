package org.cornutum.tcases.generator.less;

import lombok.Data;

/**
 * 减少用例的规则
 */
@Data
public class LessenRules {
    private boolean checkDefined;
    private boolean checkLength;
    private boolean checkType;
    private boolean useValidTuples;
    private boolean useFailureTuples;
}
