package com.github.simp7.commentdict;

import java.util.ArrayList;
import java.util.List;

public class Keyword {
    private String word;
    private List<Comment> comments;

    public String getWord() {
        return word;
    }
    public void setWord(String input) {
        this.word = input;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> input) {
        this.comments = new ArrayList<>(input);
    }

}
