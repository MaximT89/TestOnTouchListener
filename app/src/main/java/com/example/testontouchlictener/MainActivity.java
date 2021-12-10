package com.example.testontouchlictener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testontouchlictener.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TAG";
    private ActivityMainBinding binding;
    private int xDelta, yDelta;
    private int currentTop, currentLeft;
    private String currentWord;
    private ArrayList<String> arrayLetters = new ArrayList<>();
    private float px;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initView();
        convertPxInDip();
        startGame();

    }

    @SuppressLint("ResourceType")
    private void initView() {
        getTouchListener(binding.letter1);
        getTouchListener(binding.letter2);
        getTouchListener(binding.letter3);
        getTouchListener(binding.letter4);
        getTouchListener(binding.letter5);
        getTouchListener(binding.letter6);
        getTouchListener(binding.letter7);
        getTouchListener(binding.letter8);
        getTouchListener(binding.letter9);
        getTouchListener(binding.letter10);

        binding.show.setOnClickListener(view -> {

            for (int i = 0; i < currentWord.length(); i++) {
                Log.d(TAG, "getTop: " + (i+1) + " " + this.findViewById(i+1).getTop());
                Log.d(TAG, "getBottom: " + (i+1) + " " + this.findViewById(i+1).getBottom());
                Log.d(TAG, "getLeft: " + (i+1) + " " + this.findViewById(i+1).getLeft());
                Log.d(TAG, "getRight: " + (i+1) + " " + this.findViewById(i+1).getRight());
            }


        });

    }

    // Конвертер dp в px
    private void convertPxInDip() {
        int dip = 1;
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        Log.d("TAG", "px: " + px);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getTouchListener(TextView view1) {
        view1.setOnTouchListener((view, motionEvent) -> {
            final int x = (int) motionEvent.getRawX();
            final int y = (int) motionEvent.getRawY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();

                    xDelta = x - lParams.leftMargin;
                    yDelta = y - lParams.topMargin;

                    break;
                }

                case MotionEvent.ACTION_UP: {

                    getCurrentCenterView(view1);

                    //todo тут нужно запустить проверку на пересечения с ячейками для букв
                    for (int i = 0; i < currentWord.length(); i++) {

                        if (this.findViewById(i+1).getTop() < currentTop && this.findViewById(i+1).getBottom() > currentTop
                                && this.findViewById(i+1).getLeft() < currentLeft && this.findViewById(i+1).getRight() > currentLeft) {



                        }

                    }


                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    if (x - xDelta + view.getWidth() <= binding.container.getWidth()
                            && y - yDelta + view.getHeight() <= binding.container.getHeight()
                            && x - xDelta >= 0
                            && y - yDelta >= 0) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();

                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                    }

                    break;
                }
            }
            binding.container.invalidate();
            return true;
        });
    }

    // Проверка пересечения
    private boolean getStatusInterSection() {
        boolean status = false;

//        if (binding.blackSquare.getTop() < currentTop && binding.blackSquare.getBottom() > currentTop
//                && binding.blackSquare.getLeft() < currentLeft && binding.blackSquare.getRight() > currentLeft) {
//            status = true;
//        }

        return status;
    }

    // Данный метод запускает новый уровень
    private void startGame() {
        binding.startGame.setOnClickListener(view -> {
            createNewWord();
            createArrayLetters();
            generateBlockWord();
            generateBlockLetters();

        });
    }

    // Присваиваем буквы для букв которые будем прикладывать
    private void generateBlockLetters() {
        binding.letter1.setText(arrayLetters.get(0).toUpperCase());
        binding.letter2.setText(arrayLetters.get(1).toUpperCase());
        binding.letter3.setText(arrayLetters.get(2).toUpperCase());
        binding.letter4.setText(arrayLetters.get(3).toUpperCase());
        binding.letter5.setText(arrayLetters.get(4).toUpperCase());
        binding.letter6.setText(arrayLetters.get(5).toUpperCase());
        binding.letter7.setText(arrayLetters.get(6).toUpperCase());
        binding.letter8.setText(arrayLetters.get(7).toUpperCase());
        binding.letter9.setText(arrayLetters.get(8).toUpperCase());
        binding.letter10.setText(arrayLetters.get(9).toUpperCase());
    }

    // Метод генерирующий случайную русскую букву
    @NonNull
    private String generateRandomLetter() {
        String dict = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"; //строка содержит все доступные символы
        int l = (int) (Math.random() * dict.length());
        return String.valueOf(dict.charAt(l));
    }

    // Метод для преобразования слова в коллекцию строк
    private void createArrayLetters() {

        arrayLetters.clear();

        char[] charArray = currentWord.toCharArray();

        for (char c : charArray) {
            arrayLetters.add(String.valueOf(c));
        }

        int j = 10 - arrayLetters.size();

        for (int i = 0; i < j; i++) {
            arrayLetters.add(generateRandomLetter());
        }

        Collections.shuffle(arrayLetters);
        Log.d(TAG, "arrayLetters: " + arrayLetters);

    }

    // Данный метод генерирует поля для букв загаданного слова
    private void generateBlockWord() {

        binding.container.removeAllViews();

        int temp = 0;

        for (int i = 0; i < currentWord.length(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(Math.round(50 * px), Math.round(50 * px));
            params.setMargins(Math.round(3 * px) + Math.round(temp * px), Math.round(3 * px), Math.round(3 * px), Math.round(3 * px));

            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_letter));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setId(i + 1);

            binding.container.addView(textView);

            temp += 55;
        }
    }

    private void generateCoordinates(View view) {
        Log.d(TAG, "view.getTop(): " + view.getTop());
        Log.d(TAG, "view.getBottom(): " + view.getBottom());
        Log.d(TAG, "view.getLeft(): " + view.getLeft());
        Log.d(TAG, "view.getRight(): " + view.getRight());
    }

    // Данным методом мы получаем текущее слово для текущего уровня
    private void createNewWord() {
        currentWord = Words.getRandomWord();
    }

    // Данным методом получаем координаты центра любого view
    private void getCurrentCenterView(View view) {
        currentTop = view.getTop() + (view.getBottom() - view.getTop()) / 2;
        currentLeft = view.getLeft() + (view.getRight() - view.getLeft()) / 2;

        Log.d(TAG, "currentTop: " + currentTop);
        Log.d(TAG, "currentLeft: " + currentLeft);
    }

}