package com.flaremars.entity;

import com.flaremars.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by FlareMars on 2015/11/9
 */
public class BitmapObject implements Serializable {

    private String id = StringUtils.INSTANCE.generateUUID();

    private String name;

    private String createTime = StringUtils.INSTANCE.getCurrentTime();

    private String url;

    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
