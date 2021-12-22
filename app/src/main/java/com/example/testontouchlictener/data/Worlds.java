package com.example.testontouchlictener.data;

import com.example.testontouchlictener.R;

import java.util.ArrayList;
import java.util.List;

public class Worlds {

    private int idWorld;
    private String nameWorld;
    private int maxLevelInWorld;
    private int bgImage;

    public Worlds(int idWorld, String nameWorld, int maxLevelInWorld, int bgImage) {
        this.idWorld = idWorld;
        this.nameWorld = nameWorld;
        this.maxLevelInWorld = maxLevelInWorld;
        this.bgImage = bgImage;
    }

    public static List<Worlds> getWorlds() {
        List<Worlds> worlds = new ArrayList<>();

        worlds.add(new Worlds(1, "Мифы древней Греции", 100, R.drawable.test_resycler_1));
        worlds.add(new Worlds(2, "Океания", 100, R.drawable.test_resycler_2));
        worlds.add(new Worlds(3, "Космос", 100, R.drawable.test_resycler_3));

        return worlds;
    }

    public int getIdWorld() {
        return idWorld;
    }

    public String getNameWorld() {
        return nameWorld;
    }

    public int getMaxLevelInWorld() {
        return maxLevelInWorld;
    }

    public int getBgImage() {
        return bgImage;
    }
}
