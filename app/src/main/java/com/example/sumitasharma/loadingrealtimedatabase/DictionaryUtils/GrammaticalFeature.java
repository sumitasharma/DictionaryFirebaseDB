package com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class GrammaticalFeature {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
