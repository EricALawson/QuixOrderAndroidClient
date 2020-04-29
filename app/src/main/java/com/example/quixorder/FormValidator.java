package com.example.quixorder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class FormValidator {
    public static ArrayList<EditText> getEditTextViews(ViewGroup group) {
        ArrayList<EditText> views = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if (view instanceof ViewGroup) {
                views.addAll(getEditTextViews((ViewGroup) view));
                continue;
            }
            if (view instanceof EditText) {
                views.add((EditText) view);
            }
        }

        return views;
    }

    public static boolean validateForm(ArrayList<EditText> views) {
        for (EditText view : views) {
            if (view.getText().toString().equals("")) {
                Log.d("validateForm", "false");
                return false;
            }
        }
        Log.d("validateForm", "true");
        return true;
    }

    public static boolean validateCreditCard(String number) {
        if (number.length() == 16 && number.matches("-?\\d+(\\.\\d+)?")) {
            return true;
        }
        return false;
    }

    public static boolean validateCVC(String number) {
        if (number.length() == 3 && number.matches("-?\\d+(\\.\\d+)?")) {
            return true;
        }
        return false;
    }

    public static void clearForm(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if (view instanceof ViewGroup) {
                clearForm((ViewGroup) view);
                continue;
            }
            if (view instanceof EditText) {
                ((EditText) view).getText().clear();
            }
        }
    }
}
