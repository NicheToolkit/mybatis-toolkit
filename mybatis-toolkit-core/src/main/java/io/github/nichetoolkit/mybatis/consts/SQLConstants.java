package io.github.nichetoolkit.mybatis.consts;


public interface SQLConstants {
    String INSERT = "INSERT";
    String UPDATE = "UPDATE";
    String SELECT = "SELECT";
    String DELETE = "DELETE";
    String AS = "AS";
    String WHERE = "WHERE";
    String LIKE = "LIKE";
    String AND = "AND";
    String OR = "OR";
    String SET = "SET";
    String IN = "IN";
    String NOT_IN = "NOT IN";
    String ORDER_BY = "ORDER BY";
    String GROUP_BY = "GROUP BY";
    String LIMIT = "LIMIT";
    String HAVING = "HAVING";

    String IS_NULL = "IS NULL";
    String IS_NOT_NULL = "IS NOT NULL";

    String Empty = "";
    String BLANK = " ";
    String COMMA = ",";
    String PERIOD = ".";
    String LINEFEED = "\n";
    String SINGLE_QUOTE = "'";
    String DOUBLE_QUOTE = "\"";

    String CONTRAST_EQ = "=";
    String CONTRAST_GT = ">";
    String CONTRAST_LT = "<";
    String CONTRAST_GTE = ">=";
    String CONTRAST_LTE = "<=";
    String CONTRAST_NEQ = "!=";

    String PERCENT = "%";
    String BRACE_LT = "(";
    String BRACE_GT = ")";

    String DO_NOTHING = "ON DUPLICATE KEY DO NOTHING";
    String DO_UPDATE = "ON DUPLICATE KEY UPDATE";
    String ON_CONFLICT_LT = "ON CONFLICT";
    String ON_CONFLICT_DO_NOTHING_GT = "DO NOTHING";
    String ON_CONFLICT_DO_UPDATE_GT = "DO UPDATE SET";

}
