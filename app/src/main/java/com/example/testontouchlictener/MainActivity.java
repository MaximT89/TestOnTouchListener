package com.example.testontouchlictener;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.testontouchlictener.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int xDelta, yDelta;
    private int currentTop, currentBottom, currentLeft, currentRight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initView();

        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(150, 150);
        params.setMarginStart(400);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.orc);
        imageView.setLayoutParams(params);
        binding.container.addView(imageView);

        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.orc);
        imageView2.setLayoutParams(params);
        binding.container.addView(imageView2);

        getTouchListener(imageView);
        getTouchListener(imageView2);

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

                    getCurrentCoordinates(view1);
                    getCurrentCenterImage(view1);
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

//                    Log.d("TAG", "ACTION_MOVE xDelta: " + x);
//                    Log.d("TAG", "ACTION_MOVE yDelta: " + y);

                    break;
                }
            }
            binding.container.invalidate();
            return true;
        });
    }

    private boolean getStatusInterSection() {
        boolean status = false;

        if (binding.blackSquare.getTop() < currentTop && binding.blackSquare.getBottom() > currentTop
                && binding.blackSquare.getLeft() < currentLeft && binding.blackSquare.getRight() > currentLeft) {
            status = true;
        }

        return status;
    }

    private void initView() {
        binding.btnAdd.setOnClickListener(view -> {

            Log.d("TAG", "blackSquare.getTop(): " + binding.blackSquare.getTop());
            Log.d("TAG", "blackSquare.getBottom(): " + binding.blackSquare.getBottom());
            Log.d("TAG", "blackSquare.getLeft(): " + binding.blackSquare.getLeft());
            Log.d("TAG", "blackSquare.getRight(): " + binding.blackSquare.getRight());

        });
    }

    private void getCurrentCoordinates(View view) {
        Log.d("TAG", "ACTION_DOWN imageView.getTop(): " + view.getTop());
        Log.d("TAG", "ACTION_DOWN imageView.getBottom(): " + view.getBottom());
        Log.d("TAG", "ACTION_DOWN imageView.getLeft(): " + view.getLeft());
        Log.d("TAG", "ACTION_DOWN imageView.getRight(): " + view.getRight());
    }

    private void getCurrentCenterImage(View view) {
        currentTop = view.getTop() + (view.getBottom() - view.getTop()) / 2;
        currentLeft = view.getLeft() + (view.getRight() - view.getLeft()) / 2;

        Log.d("TAG", "currentTop: " + currentTop);
        Log.d("TAG", "currentLeft: " + currentLeft);
    }

}