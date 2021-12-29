package com.example.testontouchlictener.presentation.alertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;

import com.example.testontouchlictener.R;
import com.example.testontouchlictener.utils.StatusEndGame;

public class DialogAfterEndLevelFragment extends AlertDialog {
    protected DialogAfterEndLevelFragment(@NonNull Context context, NavController navController, String statusEndGame) {
        super(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_alert_dialog, null);
        builder.setView(view);

        AlertDialog alert = builder.create();

        if (alert.getWindow() != null)
            alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

        alert.show();

        if (statusEndGame.equalsIgnoreCase(StatusEndGame.WIN.toString())) {
            //todo описать логику поражения



        } else {
            //todo описать логику победы


        }
    }
}