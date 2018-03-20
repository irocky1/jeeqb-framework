package org.jeeqb.framework;

import org.jeeqb.framework.core.ConfigHelper;

/**
 * 全局常量类 
 * 
 * @author rocky
 * 2017年10月31日
 */
public interface JEEQB {

    /**
     * 字符编码
     */
	String UTF_8 = "UTF-8";
    /**
     * 框架基本配置文件
     */
    String CONFIG_PROPS = "jeeqb.properties";
    /**
     * SQL配置文件
     */
    String SQL_PROPS = "jeeqb-sql.properties";
    /**
     * 插件包路径
     */
    String PLUGIN_PACKAGE = "org.jeeqb.plugin";
    /**
     * 数据库主键标识
     */
    String PK_NAME = "id";

    /**
     * web相关配置
     */
    String JSP_PATH = ConfigHelper.getString("jeeqb.app.jsp_path", "/WEB-INF/jsp/");
    String WWW_PATH = ConfigHelper.getString("jeeqb.app.www_path", "/www/");
    String HOME_PAGE = ConfigHelper.getString("jeeqb.app.home_page", "/index.html");
    int UPLOAD_LIMIT = ConfigHelper.getInt("jeeqb.app.upload_limit", 10);

}
