package org.jeeqb.framework.ds.impl;

import org.jeeqb.framework.core.ConfigHelper;
import org.jeeqb.framework.ds.DataSourceFactory;

import javax.sql.DataSource;

/**
 * 抽象数据源工厂
 * Created by rocky on 2018/3/19.
 */
public abstract class AbstractDataSourceFactory<T extends DataSource> implements DataSourceFactory {

    protected final String driver = ConfigHelper.getString("jdbc.driver");
    protected final String url = ConfigHelper.getString("jdbc.url");
    protected final String username = ConfigHelper.getString("jdbc.username");
    protected final String password = ConfigHelper.getString("jdbc.password");

    @Override
    public final T getDataSource(){
        //创建数据源对象
        T ds = createDataSource();
        //设置基础属性
        setDriver(ds,driver);
        setUrl(ds,url);
        setUsername(ds,username);
        setPassword(ds,password);

        //设置高级属性
        setAdvancedConfig(ds);
        
        return ds;
    }

    protected abstract void setAdvancedConfig(T ds);

    protected abstract void setPassword(T ds, String password);

    protected abstract void setUsername(T ds, String username);

    protected abstract void setUrl(T ds, String url);

    protected abstract void setDriver(T ds, String driver);

    protected abstract T createDataSource();

}
