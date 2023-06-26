package com.pratice.myapp.model;

import java.io.Serializable;
import java.util.List;

public class ResponseModel implements Serializable {
    List<Anime> data;
    MetaDataModel meta;

    public ResponseModel() {
    }

    public List<Anime> getData() {
        return data;
    }

    public void setData(List<Anime> data) {
        this.data = data;
    }

    public MetaDataModel getMeta() {
        return meta;
    }

    public void setMeta(MetaDataModel meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "data=" + data +
                ", meta=" + meta +
                '}';
    }
}
