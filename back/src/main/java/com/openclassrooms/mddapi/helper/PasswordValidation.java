package com.openclassrooms.mddapi.helper;

import java.util.regex.Pattern;

public class PasswordValidation {

    public static boolean validatePassword(String password) {
        Pattern lowerCasePattern = Pattern.compile("^.*[a-z].*$");
        Pattern upperCasePattern = Pattern.compile("^.*[A-Z].*$");
        Pattern digitPattern = Pattern.compile("^.*\\d.*$");
        Pattern specialCharPattern = Pattern.compile("^.*[^a-zA-Z0-9].*$");

        return password.length() >= 8
                && lowerCasePattern.matcher(password).matches()
                && upperCasePattern.matcher(password).matches()
                && digitPattern.matcher(password).matches()
                && specialCharPattern.matcher(password).matches();
    }
}
