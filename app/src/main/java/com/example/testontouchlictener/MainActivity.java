package com.example.testontouchlictener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testontouchlictener.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TAG";
    private ActivityMainBinding binding;
    private int xDelta, yDelta, displayW, displayH;
    private int currentTop, currentLeft, startLeftMargin, startTopMargin, currentLife;
    private String currentWord, currentQuestion;
    private ArrayList<String> arrayLetters = new ArrayList<>();
    private float px;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initView();
        convertPxInDip();
        getCurrentMetrics();
        startGame();

    }

    // Данным методом получам ширину и высоту текущего дисплея
    private void getCurrentMetrics() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayW = size.x;
        displayH = size.y;

        Log.d(TAG, "displayW: " + displayW);
        Log.d(TAG, "displayH: " + displayH);
    }

    @SuppressLint("ResourceType")
    private void initView() {

        // todo это тестовые нужно потом удалить
        binding.show.setOnClickListener(view -> {
            for (int i = 0; i < currentWord.length(); i++) {
                Log.d(TAG, "getTop: " + (i + 1) + " " + this.findViewById(i + 1).getTop());
                Log.d(TAG, "getBottom: " + (i + 1) + " " + this.findViewById(i + 1).getBottom());
                Log.d(TAG, "getLeft: " + (i + 1) + " " + this.findViewById(i + 1).getLeft());
                Log.d(TAG, "getRight: " + (i + 1) + " " + this.findViewById(i + 1).getRight());
            }
        });
    }

    // Конвертер dp в px
    private void convertPxInDip() {
        int dip = 1;
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        Log.d("TAG", "px: " + px);
    }

    // Обработка касания к буквам
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

                    startLeftMargin = lParams.leftMargin;
                    startTopMargin = lParams.topMargin;

                    Log.d(TAG, "lParams.leftMargin: " + lParams.leftMargin);
                    Log.d(TAG, "lParams.topMargin: " + lParams.topMargin);
                    Log.d(TAG, "x: " + x);
                    Log.d(TAG, "y: " + y);

                    break;
                }

                case MotionEvent.ACTION_UP: {

                    getCurrentCenterView(view1);

                    //todo тут нужно запустить проверку на пересечения с ячейками для букв
                    for (int i = 0; i < currentWord.length(); i++) {

                        if (this.findViewById(i + 1).getTop() < currentTop && this.findViewById(i + 1).getBottom() > currentTop
                                && this.findViewById(i + 1).getLeft() < currentLeft && this.findViewById(i + 1).getRight() > currentLeft) {

                            if (view1.getText().toString().equalsIgnoreCase(String.valueOf(currentWord.charAt(i)))) {
                                Log.d(TAG, "getTouchListener: " + "Есть совпадение");

                                TextView textView = this.findViewById(i+1);
                                textView.setText(String.valueOf(currentWord.charAt(i)));

                                view1.setVisibility(View.INVISIBLE);

                            } else {
                                updateCurrentLife(1);
                                Log.d(TAG, "getTouchListener: " + "Нет совпадения");
                            }
                        } else {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();

                            layoutParams.leftMargin = startLeftMargin;
                            layoutParams.topMargin = startTopMargin;
                            layoutParams.rightMargin = 0;
                            layoutParams.bottomMargin = 0;
                            view1.setLayoutParams(layoutParams);
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

    // Данный метод запускает новый уровень
    private void startGame() {
        binding.startGame.setOnClickListener(view -> {
            clearGameField();
            createNewWord();
            createCurrentLife();
            updateCurrentLife(0);
            createArrayLetters();
            generateBlockWord();
            generateQuestionText();
            generateBlockLetters();
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateCurrentLife(int i) {
        currentLife = currentLife - i;
        binding.textStatus.setText("Жизни " + currentLife);
    }

    private void createCurrentLife() {
        currentLife = 3;
    }

    // Метод для генерации TextView с текущим вопросом
    private void generateQuestionText() {
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(Math.round(15 * px), Math.round(15 * px), Math.round(15 * px), Math.round(3 * px));

        TextView textView = new TextView(this);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(currentQuestion);

        binding.container.addView(textView);
    }

    // Очищаем все элементы из контейнера в котором гуляют буквы
    private void clearGameField() {
        binding.container.removeAllViews();
    }

    // Присваиваем буквы для букв которые будем прикладывать
    private void generateBlockLetters() {
        int temp = Math.round(((displayW - ((5 * Math.round(50 * px)) + (Math.round((5 * 6) * px)))) >> 1)/px);
        int temp1 = temp;
        int temp2 = temp;

        for (int i = 0; i < 10; i++) {
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(Math.round(50 * px), Math.round(50 * px));

            if(i < 5){
                params.setMargins(Math.round(3 * px) + Math.round(temp1 * px), Math.round(370 * px), Math.round(3 * px), Math.round(3 * px));
                temp1 += 56;
            } else {
                params.setMargins(Math.round(3 * px) + Math.round(temp2 * px), Math.round(426 * px), Math.round(3 * px), Math.round(3 * px));
                temp2 += 56;
            }

            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_letter));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setId(i + 1 + 100);

            binding.container.addView(textView);
            textView.setVisibility(View.VISIBLE);
            textView.setText(arrayLetters.get(i).toUpperCase());
            getTouchListener(textView);
        }
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

        for (char c : charArray) arrayLetters.add(String.valueOf(c));

        int j = 10 - arrayLetters.size();

        for (int i = 0; i < j; i++) arrayLetters.add(generateRandomLetter());

        Collections.shuffle(arrayLetters);
        Log.d(TAG, "arrayLetters: " + arrayLetters);
    }

    // Данный метод генерирует поля для букв загаданного слова
    private void generateBlockWord() {
        int temp = Math.round(((displayW - ((currentWord.length() * Math.round(50 * px)) + (Math.round((currentWord.length() * 6) * px)))) >> 1)/px);

        for (int i = 0; i < currentWord.length(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(Math.round(50 * px), Math.round(50 * px));
            params.setMargins(Math.round(3 * px) + Math.round(temp * px), Math.round(150 * px), Math.round(3 * px), Math.round(3 * px));

            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_letter));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setId(i + 1);

            binding.container.addView(textView);

            temp += 56;
        }
    }

    // todo тестовый метод для отображения координат любого view в лог
    private void getCoordinate(View view) {
        Log.d(TAG, "view.getTop(): " + view.getTop());
        Log.d(TAG, "view.getBottom(): " + view.getBottom());
        Log.d(TAG, "view.getLeft(): " + view.getLeft());
        Log.d(TAG, "view.getRight(): " + view.getRight());
    }

    // Данным методом мы получаем текущее слово для текущего уровня
    private void createNewWord() {
        Words word = Words.getRandomWord();
        currentWord = word.getWord();
        currentQuestion = word.getQuestions();
    }

    // Данным методом получаем координаты центра любого view
    private void getCurrentCenterView(View view) {
        currentTop = view.getTop() + (view.getBottom() - view.getTop()) / 2;
        currentLeft = view.getLeft() + (view.getRight() - view.getLeft()) / 2;

        Log.d(TAG, "currentTop: " + currentTop);
        Log.d(TAG, "currentLeft: " + currentLeft);
    }

}