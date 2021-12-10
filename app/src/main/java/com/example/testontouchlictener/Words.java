package com.example.testontouchlictener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Words {

    private String word;

    public Words(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public static String getRandomWord(){
        String word = "";
        List<Words> arrayWords = new ArrayList<>();
        arrayWords.add(new Words("ПИСЬМО"));
        arrayWords.add(new Words("ОКНО"));
        arrayWords.add(new Words("КРУЖКА"));
        arrayWords.add(new Words("ТЕЛЕФОН"));
        arrayWords.add(new Words("МАМА"));
        arrayWords.add(new Words("НЕБО"));

        word = arrayWords.get(new Random().nextInt(arrayWords.size())).getWord();

        return word;
    }
}
