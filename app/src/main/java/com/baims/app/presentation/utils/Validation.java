package com.baims.app.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.presentation.common.Constants;

import java.util.Calendar;
import java.util.Date;

public class Validation {
    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static Boolean isValidString(EditText edt, String message)
            throws NumberFormatException {
        if (edt.getText().toString().trim().length() <= 0) {
            edt.setError(Html
                    .fromHtml("<font color='white', style='background-color:red'>" + message + "</font>"));
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidMobileNumber(EditText mobile_number_ed, Context activity) {
        if (!isValidString(mobile_number_ed, activity.getString(R.string.required)))
            return false;
        Log.i("moblie",  mobile_number_ed.getText().toString());
        if (!(mobile_number_ed.getText().toString()).matches(Constants.PhoneNumRegEx)) {
            mobile_number_ed.setError(activity.getString(R.string.invalid_mobile_number));
            return false;
        }

        return true;
    }

    public static Boolean isValidString(EditText edt, TextInputLayout textInputLayout, String message)
            throws NumberFormatException {
        if (edt.getText().toString().trim().length() <= 0) {
            textInputLayout.setError(message);
            //edt.setError(message);
//            edt.setError(Html
//                    .fromHtml("<font color='white', style='background-color:red'>" + message + "</font>"));
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }

    public static void showEditTextError(EditText edt, String message) {
        if (edt != null)
            edt.setError(Html
                    .fromHtml("<font color='white', style='background-color:red'>" + message + "</font>"));

    }

    public static boolean isValidEmail(EditText edt, Activity activity) {
        String message;
        if (edt.getText().toString().trim().isEmpty()) {
            message = activity.getString(R.string.required) + " " + activity.getString(R.string.email);
            edt.setError(message);
            edt.setError(Html
                    .fromHtml("<font color='red'>" + message + "</font>"));
            return false;
        } else if (!isEmailValid(edt.getText().toString())) {
            message = activity.getString(R.string.invalidEmailAddress);
            edt.setError(Html
                    .fromHtml("<font color='red'>" + message + "</font>"));
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidEmail(EditText edt, String message) {
        if (edt.getText().toString().trim().isEmpty()) {
            edt.setError(message);
            edt.setError(Html
                    .fromHtml("<font color='red'>" + message + "</font>"));
            return false;
        } else if (!isEmailValid(edt.getText().toString())) {
            edt.setError(Html
                    .fromHtml("<font color='red'>" + message + "</font>"));
            return false;
        } else {
            return true;
        }
    }


    public static Boolean isValidEmail(TextInputEditText edt, String message, TextInputLayout textInputLayout)
            throws NumberFormatException {
        if (!isEmailValid(edt.getText().toString())) {
            textInputLayout.setError(message);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }

    private static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isValidPassword(int charNum, EditText edt, Activity activity) {
        String message;
        if (edt.getText().toString().isEmpty()) {
            message = activity.getString(R.string.require_field);
            edt.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            return false;
        } else if (edt.getText().toString().trim().length() < charNum) {
            message = activity.getString(R.string.NewPasswordLessThan4);
            edt.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            return false;
        } else
            return true;
    }

    public static boolean isValidPassword(int charNum, EditText edt, Activity activity, TextInputLayout textInputLayout) {
        String message;
        if (edt.getText().toString().isEmpty()) {
            message = activity.getString(R.string.require_field);
            textInputLayout.setError(message);
            return false;
        } else if (edt.getText().toString().trim().length() < charNum) {
            message = activity.getString(R.string.NewPasswordLessThan4);
            textInputLayout.setError(message);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }

    public static boolean isValidٍFieldLenght(int charNum, EditText edt, String message) {

        if (!edt.getText().toString().isEmpty()
                && edt.getText().toString().trim().length() >= charNum) {
            return true;
        } else
            edt.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));


        return false;
    }

    public static boolean ConfirmPassword(EditText password,
                                          EditText confirmPassword,
                                          String message) {
        boolean pStatus = false;
        if (!confirmPassword.getText().toString().isEmpty()
                && !password.getText().toString().isEmpty()) {
            if (password.getText().toString().trim()
                    .equals(confirmPassword.getText().toString().trim())) {
                pStatus = true;
            } else {
                confirmPassword.setError(Html
                        .fromHtml("<font color='red'>" + message + "</font>"));
            }
        }
        return pStatus;
    }

    public static boolean isValidBirthDate(Date selectedDate, String message, TextView view) {
        Calendar calendar = Calendar.getInstance();
        Calendar date = null;
        if (selectedDate != null) {
            date = Calendar.getInstance();
            date.setTime(selectedDate);
        }

        if (date != null && date.getTime() != null
                && (date.getTime().before(calendar.getTime())
                || !date.getTime().after(calendar.getTime()))) {
            return true;
        } else {
            view.setError(Html
                    .fromHtml("<font color='red'>" + message + "</font>"));
        }

        return false;
    }

    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0;
    }

    private static boolean validateEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

}
