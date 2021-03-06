package com.example.testontouchlictener.presentation.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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

import com.example.testontouchlictener.R;
import com.example.testontouchlictener.data.Words;
import com.example.testontouchlictener.databinding.FragmentGameBinding;

import java.util.ArrayList;
import java.util.Collections;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;

    private String TAG = "TAG";
    private int xDelta, yDelta, displayW, displayH;
    private int currentTop, currentLeft, startLeftMargin, startTopMargin, currentLife;
    private String currentWord, currentQuestion;
    private ArrayList<String> arrayLetters = new ArrayList<>();
    private float px;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(LayoutInflater.from(requireParentFragment().getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        convertPxInDip();
        getCurrentMetrics();
        startGame();
    }

    // Данным методом получам ширину и высоту текущего дисплея
    private void getCurrentMetrics() {
        Display display = requireActivity().getWindowManager().getDefaultDisplay();
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
                Log.d(TAG, "getTop: " + (i + 1) + " " + requireActivity().findViewById(i + 1).getTop());
                Log.d(TAG, "getBottom: " + (i + 1) + " " + requireActivity().findViewById(i + 1).getBottom());
                Log.d(TAG, "getLeft: " + (i + 1) + " " + requireActivity().findViewById(i + 1).getLeft());
                Log.d(TAG, "getRight: " + (i + 1) + " " + requireActivity().findViewById(i + 1).getRight());
            }
        });
    }

    // Конвертер dp в px
    private void convertPxInDip() {
        int dip = 1;
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        Log.d("TAG", "px: " + px);
    }

    // Обновляем отгадываемое слово
    private void updateTempWord(){
        String tempWord = "";

        // тут составляем временное слово которое отгадывает пользователь
        for (int i = 0; i < currentWord.length(); i++) {
            TextView textView = requireActivity().findViewById(i + 1);
            if(!TextUtils.isEmpty(textView.getText())){
                tempWord += textView.getText().toString();
            }
        }

        if(tempWord.equalsIgnoreCase(currentWord)){
            //todo тут нужен метод для победы в уровне
            binding.textStatus.setText("Поздравляем вы выйграли"); // для теста
        }

        Log.d(TAG, "tempWord: " + tempWord);
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

                    for (int i = 0; i < currentWord.length(); i++) {

                        if (requireActivity().findViewById(i + 1).getTop() < currentTop && requireActivity().findViewById(i + 1).getBottom() > currentTop
                                && requireActivity().findViewById(i + 1).getLeft() < currentLeft && requireActivity().findViewById(i + 1).getRight() > currentLeft) {

                            if (view1.getText().toString().equalsIgnoreCase(String.valueOf(currentWord.charAt(i)))) {
                                Log.d(TAG, "getTouchListener: " + "Есть совпадение");

                                TextView textView = requireActivity().findViewById(i+1);
                                textView.setText(String.valueOf(currentWord.charAt(i)));

                                view1.setVisibility(View.INVISIBLE);
                                updateTempWord();

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

        if (currentLife == 0){
            //todo тут нужен метод для поражения в игре (когда закончились жизни)
        }
    }

    private void createCurrentLife() {
        currentLife = 3;
    }

    // Метод для генерации TextView с текущим вопросом
    private void generateQuestionText() {
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(Math.round(15 * px), Math.round(40 * px), Math.round(15 * px), Math.round(3 * px));

        TextView textView = new TextView(requireActivity());
        textView.setLayoutParams(params);
        textView.setTextAppearance(R.style.CustomTextViewWhite);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
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

            TextView textView = new TextView(requireActivity());
            textView.setLayoutParams(params);

            // todo сделать разные стили для букв под разные блоки с уровнями
            textView.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.border_letter));
            textView.setTextAppearance(R.style.CustomTextViewWithoutShadowBlack);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
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

            TextView textView = new TextView(requireActivity());
            textView.setLayoutParams(params);

            // todo сделать разные стили для букв под разные блоки с уровнями
            textView.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.border_letter));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setId(i + 1);

            binding.container.addView(textView);

            temp += 56;
        }
    }

    // тестовый метод для отображения координат любого view в лог
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
    }
}