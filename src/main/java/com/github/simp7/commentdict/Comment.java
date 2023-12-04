package com.github.simp7.commentdict;

public class Comment {
    private int id;
    private int popularity;
    private String content;
    private boolean isMine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsMine() {
        return this.isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
