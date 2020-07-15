package com.mythmayor.basicproject.bean;

import java.util.Objects;

/**
 * Created by mythmayor on 2020/7/15.
 */
public class SearchHistoryBean extends BaseBean {

    private int id;
    private String name;

    public SearchHistoryBean() {
    }

    public SearchHistoryBean(String name) {
        this.name = name;
    }

    public SearchHistoryBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistoryBean that = (SearchHistoryBean) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
