package com.nikolmanolova.dictionary.data;

public class HistoryEntity {
    public static final String TABLE_NAME = "HISTORY";
    public static final String ID_COLUMN = "ID";
    public static final String WORD_COLUMN = "WORD";

    private int id;
    private String word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
