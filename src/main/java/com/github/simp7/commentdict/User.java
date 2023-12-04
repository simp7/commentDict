package com.github.simp7.commentdict;

public class User {
    private int uid;
    private String id;
    private String passwd;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return this.id; }
    public void setPasswd(String passwd) { this.passwd = passwd; }
    public String getPasswd() { return this.passwd; }
}
