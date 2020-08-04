package com.mythmayor.basicproject.bean;

/**
 * Created by mythmayor on 2020/7/28.
 */
public class EventBusBean extends BaseBean {

    private String key;
    private Object value;

    public EventBusBean() {
    }

    public EventBusBean(String key) {
        this.key = key;
    }

    public EventBusBean(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
