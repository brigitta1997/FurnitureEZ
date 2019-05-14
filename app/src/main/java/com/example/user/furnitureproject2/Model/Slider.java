package com.example.user.furnitureproject2.Model;

public class Slider {
    private int objId;
    private String imgLink;

    public Slider(int objId, String imgLink) {
        this.objId = objId;
        this.imgLink = imgLink;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
