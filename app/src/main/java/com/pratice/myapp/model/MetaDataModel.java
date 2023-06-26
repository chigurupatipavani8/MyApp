package com.pratice.myapp.model;

import java.io.Serializable;

public class MetaDataModel implements Serializable {
    int page;
    int size;
    int totalData;
    int totalPage;

    public MetaDataModel() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "MetaDataModel{" +
                "page=" + page +
                ", size=" + size +
                ", totalData=" + totalData +
                ", totalPage=" + totalPage +
                '}';
    }
}
