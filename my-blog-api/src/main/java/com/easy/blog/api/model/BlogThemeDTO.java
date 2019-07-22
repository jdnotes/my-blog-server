package com.easy.blog.api.model;

import java.io.Serializable;
import java.util.Date;

public class BlogThemeDTO implements Serializable {

    private Byte hot;

    private Byte quality;

    public Byte getHot() {
        return hot;
    }

    public void setHot(Byte hot) {
        this.hot = hot;
    }

    public Byte getQuality() {
        return quality;
    }

    public void setQuality(Byte quality) {
        this.quality = quality;
    }
}