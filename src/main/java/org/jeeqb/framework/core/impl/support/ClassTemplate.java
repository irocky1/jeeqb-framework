package org.jeeqb.framework.core.impl.support;

import org.jeeqb.framework.util.ClassUtil;
import org.jeeqb.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 用于获取类的模板类
 * Created by rocky on 2017/12/29.
 */
public abstract class ClassTemplate {

    private static final Logger logger = LoggerFactory.getLogger(ClassTemplate.class);

    protected final String packageName;

    protected ClassTemplate(String packageName){
        this.packageName = packageName;
    }

    public final List<Class<?>> getClassList(){
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            //1.从包名获取URL类型的资源
            Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace(".", "/"));
            //2.遍历URL资源
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url!=null){
                    //3.获取协议名，分为file,jar
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")){
                        //若在class目录中，则执行类添加操作
                        String packagePath = url.getPath().replaceAll("%20"," ");
                        addClass(classList,packagePath,packageName);
                    }else if (protocol.equals("jar")){
                        //若在jar包中，则解析jar包中的entry
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()){
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            //判断该entry 是否为class
                            if(jarEntryName.endsWith(".class")){
                                //获取类名
                                String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                //执行添加类操作
                                doAddClass(classList,className);
                            }
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classList;
    }

    private void addClass(List<Class<?>> classList, String packagePath, String packageName) {

        try {
            //1.获取包名路径下的class文件或目录
            File[] files = new File(packagePath).listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
                }
            });
            //2.遍历文件或目录
            for (File file : files) {
                String fileName = file.getName();
                //2.1判断是否为文件或目录
                if (file.isFile()) {
                    //2.1.1获取类名
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (StringUtil.isNotEmpty(packageName)) {
                        className = packageName + "." + className;
                    }
                    //2.1.2执行添加类操作
                    doAddClass(classList, className);
                } else {
                    //2.2.1获取子包
                    String subPackagePath = fileName;
                    if (StringUtil.isNotEmpty(packagePath)) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }
                    //2.2.2子包名
                    String subPackageName = packageName;
                    if (StringUtil.isNotEmpty(packageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    addClass(classList, subPackagePath, subPackageName);
                }
            }
        }catch (Exception e){
            logger.error("获取类出错!",e);
        }
    }

    private void doAddClass(List<Class<?>> classList, String className) {
        //1.加载类
        Class<?> cls = ClassUtil.loadClass(className,false);
        //判断是否可以添加类
        if(checkAddClass(cls)){
            classList.add(cls);
        }
    }

    /**
     * 验证是否允许添加类
     * @param cls
     * @return
     */
    protected abstract boolean checkAddClass(Class<?> cls);

}
