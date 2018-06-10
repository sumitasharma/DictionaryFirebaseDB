package com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils;


public class Dictionary {
    private String word;
    private String wordMeaning;

    public Dictionary(String word, String meaning) {
        this.word = word;
        this.wordMeaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordMeaning() {
        return wordMeaning;
    }

    public void setWordMeaning(String wordMeaning) {
        this.wordMeaning = wordMeaning;
    }
}
