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
        arrayWords.add(new Words("письмо"));
        arrayWords.add(new Words("окно"));
        arrayWords.add(new Words("кружка"));
        arrayWords.add(new Words("телефон"));
        arrayWords.add(new Words("мама"));
        arrayWords.add(new Words("небо"));

        word = arrayWords.get(new Random().nextInt(arrayWords.size())).getWord();

        return word;
    }
}
