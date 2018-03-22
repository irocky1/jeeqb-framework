package org.jeeqb.framework.mvc.bean;

import org.jeeqb.framework.core.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量上传文件对象
 * Created by rocky on 2018/3/21.
 */
public class Multiparts extends BaseBean {

    List<Multipart> multipartList = new ArrayList<>();

    public Multiparts(List<Multipart> multipartList) {
        this.multipartList = multipartList;
    }

    public int size() {
        return multipartList.size();
    }

    public List<Multipart> getAll() {
        return multipartList;
    }

    public Multipart getOne() {
        return size() > 0 ? multipartList.get(0) : null;
    }
}
