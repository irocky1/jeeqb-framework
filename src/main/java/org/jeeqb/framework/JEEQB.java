package org.jeeqb.framework;

import org.jeeqb.framework.core.ConfigHelper;

/**
 * 全局常量类 
 * 
 * @author rocky
 * 2017年10月31日
 */
public interface JEEQB {

	String UTF_8 = "UTF-8";
    String CONFIG_PROPS = "jeeqb.properties";
    String SQL_PROPS = "jeeqb-sql.properties";
    String PLUGIN_PACKAGE = "org.jeeqb.plugin";

    String JSP_PATH = ConfigHelper.getString("jeeqb.app.jsp_path", "/WEB-INF/jsp/");
    String WWW_PATH = ConfigHelper.getString("jeeqb.app.www_path", "/www/");
    String HOME_PAGE = ConfigHelper.getString("jeeqb.app.home_page", "/index.html");
    int UPLOAD_LIMIT = ConfigHelper.getInt("jeeqb.app.upload_limit", 10);

    String PK_NAME = "id";

}
