package io.github.nichetoolkit.mybatis.consts;


public interface ScriptConstants {
    String PARAM = "_parameter";

    /* "<script>\n%s\n</script>" */
    String SCRIPT_LABEL = "<script>\n%s\n</script>";
    String WHERE_LABEL = "\n<where>%s\n</where> ";
    String CHOOSE_LABEL = "\n<choose>%s\n</choose> ";
    String OTHERWISE_LABEL = "\n<otherwise>%s\n</otherwise> ";
    String SET_LABEL = "\n<set>%s\n</set> ";
    String IF_TEST_LABEL = "\n<if test=\"%s\">%s\n</if> ";

    String IF_TEST_PARAM_LABEL = "\n<if test=\"_parameter != null and _parameter != ''\">%s\n</if> ";
    String WHEN_TEST_LABEL = "\n<when test=\"%s\">%s\n</when> ";

    String TRIM_LABEL = "\n<trim prefix=\"%s\" prefixOverrides=\"%s\" suffixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ";

    String TRIM_PREFIX_OVERRIDE_LABEL = "\n<trim prefix=\"%s\" prefixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ";

    String TRIM_SUFFIX_OVERRIDE_LABEL = "\n<trim prefix=\"%s\" suffixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ";

    String FOREACH_LABEL = "\n<foreach collection=\"%s\" item=\"%s\">%s\n</foreach> ";
    String FOREACH_SEPARATOR_LABEL = "\n<foreach collection=\"%s\" item=\"%s\" separator=\"%s\">%s\n</foreach> ";
    String FOREACH_BRACE_LABEL = "\n<foreach collection=\"%s\" item=\"%s\" open=\"%s\" close=\"%s\" separator=\"%s\">%s\n</foreach> ";
    String FOREACH_INDEX_LABEL = "\n<foreach collection=\"%s\" item=\"%s\" index=\"%s\" open=\"%s\" close=\"%s\" separator=\"%s\">%s\n</foreach> ";
    String BIND_LABEL = "\n<bind name=\"%s\" value=\"%s\"/>";
}
