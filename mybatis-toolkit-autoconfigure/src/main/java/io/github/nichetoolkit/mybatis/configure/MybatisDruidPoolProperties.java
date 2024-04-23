package io.github.nichetoolkit.mybatis.configure;

/**
 * <p>DruidConfig</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisDruidPoolProperties {
    /** 初始化核心线程大小 */
    private Integer coreSize = 5;
    /** 最小空闲线程大小 */
    private Integer minIdleSize = 10;
    /** 最大工作线程大小 */
    private Integer maxWorkSize = 20;
    /** 连接等待超时的时间 */
    private Integer maxWaitTime = 60000;
    /** 空闲连接检测心跳时间*/
    private Integer checkBeatTime = 60000;
    /** 连接最小生存时间 */
    private Integer minLiveTime = 30000;
    /** 连接最大生存时间 */
    private Integer maxLiveTime = 90000;
    /** 测试查询SQL */
    private String testSql = "SELECT 1";
    /** 空闲时测试 */
    private Boolean idleTest = true;
    /** 申请连接时测试 */
    private Boolean applyTest = false;
    /** 归还连接时测试 */
    private Boolean revertTest = false;

    public MybatisDruidPoolProperties() {
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getMinIdleSize() {
        return minIdleSize;
    }

    public void setMinIdleSize(Integer minIdleSize) {
        this.minIdleSize = minIdleSize;
    }

    public Integer getMaxWorkSize() {
        return maxWorkSize;
    }

    public void setMaxWorkSize(Integer maxWorkSize) {
        this.maxWorkSize = maxWorkSize;
    }

    public Integer getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Integer getCheckBeatTime() {
        return checkBeatTime;
    }

    public void setCheckBeatTime(Integer checkBeatTime) {
        this.checkBeatTime = checkBeatTime;
    }

    public Integer getMinLiveTime() {
        return minLiveTime;
    }

    public void setMinLiveTime(Integer minLiveTime) {
        this.minLiveTime = minLiveTime;
    }

    public Integer getMaxLiveTime() {
        return maxLiveTime;
    }

    public void setMaxLiveTime(Integer maxLiveTime) {
        this.maxLiveTime = maxLiveTime;
    }

    public String getTestSql() {
        return testSql;
    }

    public void setTestSql(String testSql) {
        this.testSql = testSql;
    }

    public Boolean getIdleTest() {
        return idleTest;
    }

    public void setIdleTest(Boolean idleTest) {
        this.idleTest = idleTest;
    }

    public Boolean getApplyTest() {
        return applyTest;
    }

    public void setApplyTest(Boolean applyTest) {
        this.applyTest = applyTest;
    }

    public Boolean getRevertTest() {
        return revertTest;
    }

    public void setRevertTest(Boolean revertTest) {
        this.revertTest = revertTest;
    }
}
