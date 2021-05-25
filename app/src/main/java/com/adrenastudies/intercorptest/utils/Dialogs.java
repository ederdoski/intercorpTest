package com.adrenastudies.intercorptest.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.adrenastudies.intercorptest.R;
import com.adrenastudies.moneelyte.interfaces.DialogCallback;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

public class Dialogs {

    private static Activity activity;

    public Dialogs(Activity _activity){
        activity = _activity;
    }

    public static LovelyStandardDialog basicDialog(int title, int message) {

        return new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.blue_50)
                .setButtonsColorRes(R.color.black)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_dialog);
    }

    public static LovelyStandardDialog basicDialog(int title, String message) {

        return new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.blue_50)
                .setButtonsColorRes(R.color.black)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_dialog);
    }

    public static LovelyInfoDialog infoDialog() {

        return new LovelyInfoDialog(activity)
                .setTopColorRes(R.color.blue_50)
                .setIcon(R.drawable.ic_dialog)
                .setNotShowAgainOptionEnabled(0)
                .setNotShowAgainOptionChecked(true);

    }

    public static LovelyCustomDialog customDialog(int layout){

        return new LovelyCustomDialog(activity)
                .setView(layout)
                .setTopColorRes(R.color.blue_50)
                .setIcon(R.drawable.ic_dialog);
    }

    public static AlertDialog loading(Context ctx) {

        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null, false);

        final DotProgressBar progressBar = dialogView.findViewById(R.id.dotProgressBar);

        progressBar.changeStartColor(ContextCompat.getColor(ctx, R.color.blue_300));
        progressBar.changeEndColor(ContextCompat.getColor(ctx, R.color.blue_500));

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        return builder.create();
    }

    public static LovelyTextInputDialog dialogActivationCode(Context ctx) {
        return new LovelyTextInputDialog(ctx)
                .setTopColorRes(R.color.blue_50)
                .setIcon(R.drawable.ic_dialog)
                .setTitle(R.string.txt_atention)
                .setMessage(R.string.dialog_token_login);
    }

}
