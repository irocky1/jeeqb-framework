package org.jeeqb.framework.ds;

import javax.sql.DataSource;

/**
 * 数据源工厂
 * Created by rocky on 2018/3/19.
 */
public interface DataSourceFactory {

    DataSource getDataSource();

}
