package com.wedog.mysql.enums;

import cn.hutool.core.util.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ly
 */

@Getter
@AllArgsConstructor
public enum OperatorEnum {
    EQUAL("==", "等于") {
        @Override
        public boolean compare(Object value, Object expect) {
            if (OperatorEnum.isNumber(value)) {
                return OperatorEnum.compareNumber(value, expect, true, true);
            } else if (value instanceof String && expect instanceof String) {
                return ((String) value).equalsIgnoreCase((String) expect);
            }
            return false;
        }
    },
    NOT_EQUAL("<>", "不等于") {
        @Override
        public boolean compare(Object value, Object expect) {
            return false;
        }
    },
    GREATER(">", "大于") {
        @Override
        public boolean compare(Object value, Object expect) {
            if (OperatorEnum.isNumber(value)) {
                return OperatorEnum.compareNumber(value, expect, false, false);
            }
            return false;
        }
    },
    GREATER_EQUAL(">=", "大于等于") {
        @Override
        public boolean compare(Object value, Object expect) {
            if (OperatorEnum.isNumber(value)) {
                return OperatorEnum.compareNumber(value, expect, true, false);
            }
            return false;
        }
    },
    LESS("<", "小于") {
        @Override
        public boolean compare(Object value, Object expect) {
            if (OperatorEnum.isNumber(value)) {
                return OperatorEnum.compareNumber(expect, value, false, false);
            }
            return false;
        }
    },
    LESS_EQUAL("<=", "小于等于") {
        @Override
        public boolean compare(Object value, Object expect) {
            if (OperatorEnum.isNumber(value)) {
                return OperatorEnum.compareNumber(expect, value, true, false);
            }
            return false;
        }
    };

    private String operator;
    private String describe;

    /**
     * 数字比较
     *
     * @param value
     * @param expect
     * @param include
     * @param onlyEqual
     * @return
     */
    private static boolean compareNumber(Object value, Object expect, boolean include, boolean onlyEqual) {
        int result = 1;
        if (include) {
            result = 0;
        }
        if (value instanceof Integer && expect instanceof Integer) {
            if (onlyEqual) {
                return NumberUtil.compare((Integer) value, (Integer) expect) == result ? true : false;
            } else {
                return NumberUtil.compare((Integer) value, (Integer) expect) >= result ? true : false;
            }
        } else if (value instanceof Short && expect instanceof Short) {
            if (onlyEqual) {
                return NumberUtil.compare((Short) value, (Short) expect) == result ? true : false;
            } else {
                return NumberUtil.compare((Short) value, (Short) expect) >= result ? true : false;
            }
        } else if (value instanceof Long && expect instanceof Long) {
            if (onlyEqual) {
                return NumberUtil.compare((Long) value, (Long) expect) == result ? true : false;
            } else {
                return NumberUtil.compare((Long) value, (Long) expect) >= result ? true : false;
            }
        } else if (value instanceof Double && expect instanceof Double) {
            if (onlyEqual) {
                return NumberUtil.compare((Double) value, (Double) expect) == result ? true : false;
            } else {
                return NumberUtil.compare((Double) value, (Double) expect) >= result ? true : false;
            }
        } else if (value instanceof Float && expect instanceof Float) {
            if (onlyEqual) {
                return NumberUtil.compare((Float) value, (Float) expect) == result ? true : false;
            } else {
                return NumberUtil.compare((Float) value, (Float) expect) >= result ? true : false;
            }
        }
        return false;
    }

    /**
     * 判断数据类型
     *
     * @param value
     * @return
     */
    private static boolean isNumber(Object value) {
        if (value instanceof Number) {
            return true;
        } else if (value instanceof String) {
            try {
                Double.parseDouble((String) value);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public abstract boolean compare(Object value, Object expect);
}
