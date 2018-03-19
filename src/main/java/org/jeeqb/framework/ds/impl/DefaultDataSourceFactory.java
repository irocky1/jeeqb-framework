package org.jeeqb.framework.ds.impl;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 默认数据源工厂实现
 *
 * 基于 Apache commons dbcp 实现
 *
 * Created by rocky on 2018/3/19.
 */
public class DefaultDataSourceFactory extends AbstractDataSourceFactory<BasicDataSource> {

    @Override
    protected void setAdvancedConfig(BasicDataSource ds) {
        // 解决 java.sql.SQLException: Already closed. 的问题（连接池会自动关闭长时间没有使用的连接）
        ds.setValidationQuery("select 1");
    }

    @Override
    protected void setPassword(BasicDataSource ds, String password) {
        ds.setPassword(password);
    }

    @Override
    protected void setUsername(BasicDataSource ds, String username) {
        ds.setUsername(username);
    }

    @Override
    protected void setUrl(BasicDataSource ds, String url) {
        ds.setUrl(url);
    }

    @Override
    protected void setDriver(BasicDataSource ds, String driver) {
        ds.setDriverClassName(driver);
    }

    @Override
    protected BasicDataSource createDataSource() {
        return new BasicDataSource();
    }
}
