package com.example.testontouchlictener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Words {

    private String word;
    private String questions;

    public Words(String word, String questions) {
        this.word = word;
        this.questions = questions;
    }

    public String getWord() {
        return word;
    }

    public String getQuestions() {
        return questions;
    }

    public static Words getRandomWord(){

        List<Words> arrayWords = new ArrayList<>();
        arrayWords.add(new Words("ПИСЬМО", "Их доставляет печкин"));
        arrayWords.add(new Words("ОКНО", "В Европу"));
        arrayWords.add(new Words("КРУЖКА", "Из нее можно пить"));
        arrayWords.add(new Words("ТЕЛЕФОН", "Без этого устройства дети звали друг друга погулять криками под окном"));
        arrayWords.add(new Words("МАМА", "Моет раму"));
        arrayWords.add(new Words("НЕБО", "В нем летают самолеты"));

        return arrayWords.get(new Random().nextInt(arrayWords.size()));
    }
}
