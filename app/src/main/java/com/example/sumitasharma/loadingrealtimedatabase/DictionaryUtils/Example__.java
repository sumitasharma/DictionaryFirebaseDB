package com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Example__ {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
