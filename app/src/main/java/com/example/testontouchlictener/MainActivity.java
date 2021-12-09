package com.example.testontouchlictener;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testontouchlictener.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int xDelta, yDelta;
    private int currentTop, currentLeft;
    private String currentWord;
    private float px;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        convertPxInDip();
        startGame();


        // todo разобрать код ниже
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(150, 150);
        params.setMarginStart(400);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.orc);
        imageView.setLayoutParams(params);
        imageView.setId(1);
        binding.container.addView(imageView);

        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.orc);
        imageView2.setLayoutParams(params);
        imageView.setId(2);
        binding.container.addView(imageView2);

        getTouchListener(imageView);
        getTouchListener(imageView2);

    }

    // Конвертер dp в px
    private void convertPxInDip() {
        int dip = 1;
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,  getResources().getDisplayMetrics());
        Log.d("TAG", "px: " + px);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getTouchListener(View view1) {
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

                    //todo тестовый код
                    if (getStatusInterSection()) binding.textStatus.setText("Есть пересечение");
                    else binding.textStatus.setText("Нет пересечения");

                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    if (x - xDelta + view.getWidth() <= binding.container.getWidth()
                            && y - yDelta + view.getHeight() <= binding.container.getHeight()
                            && x - xDelta >= 0
                            && y - yDelta >= 0) {
                        FrameLayout.LayoutParams layoutParams =
                                (FrameLayout.LayoutParams) view.getLayoutParams();
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
            generateBlockLetters();
        });
    }

    // Данный метод генерирует поля для букв загаданного слова
    private void generateBlockLetters() {

        binding.blockWord.removeAllViews();

        for (int i = 0; i < currentWord.length(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(Math.round(50 * px), Math.round(50 * px));
            params.setMargins(Math.round(3 * px), Math.round(3 * px), Math.round(3 * px), Math.round(3 * px));

            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_letter));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setId(i);

            binding.blockWord.addView(textView);
        }
    }

    // Данным методом мы получаем текущее слово для текущего уровня
    private void createNewWord() {
        currentWord = Words.getRandomWord();
    }

    // Данным методом получаем координаты центра любого view
    private void getCurrentCenterView(View view) {
        currentTop = view.getTop() + (view.getBottom() - view.getTop()) / 2;
        currentLeft = view.getLeft() + (view.getRight() - view.getLeft()) / 2;
    }

}