package com.example.testontouchlictener.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Words {

    private int world;
    private int level;
    private String word;
    private String questions;

    public Words(int world, int level, String word, String questions) {
        this.world = world;
        this.level = level;
        this.word = word;
        this.questions = questions;
    }

    public int getLevel() {
        return level;
    }

    public int getWorld() {
        return world;
    }

    public String getWord() {
        return word;
    }

    public String getQuestions() {
        return questions;
    }

    public static Words getRandomWord() {
        List<Words> arrayWords = new ArrayList<>();
        arrayWords.add(new Words(1, 1, "ПИСЬМО", "Их доставляет печкин"));
        arrayWords.add(new Words(1, 2, "ОКНО", "В Европу"));
        arrayWords.add(new Words(1, 3, "КРУЖКА", "Из нее можно пить"));
        arrayWords.add(new Words(1, 4, "ТЕЛЕФОН", "Без этого устройства дети звали друг друга погулять криками под окном"));
        arrayWords.add(new Words(1, 5, "МАМА", "Моет раму"));
        arrayWords.add(new Words(1, 6, "НЕБО", "В нем летают самолеты"));

        return arrayWords.get(new Random().nextInt(arrayWords.size()));
    }
}
