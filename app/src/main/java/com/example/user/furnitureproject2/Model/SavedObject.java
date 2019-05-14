package com.example.user.furnitureproject2.Model;

public class SavedObject {
    private int userId;
    private int objId;

    public SavedObject(int userId, int objId) {
        this.userId = userId;
        this.objId = objId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }
}
