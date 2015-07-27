package teamproject.rmm2.Helpers;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by Marcin on 2015-07-15.
 * Validates device with token, password and login
 */
public class Validate {
    //TODO check whole Validate class. I just copied it (MR)
    // Regular Expression
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
    /*
            Password matching expression.
            Password must be at least 4 characters, no more than 8 characters, and must include at least one upper case letter, one lower case letter, and one numeric digit.
    */

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PASSWORD_MSG = "invalid password";

    public Validate() {
    }

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isPassword(EditText editText, boolean required) {
        return isValid(editText, PASSWORD_REGEX, PASSWORD_MSG, required);
    }

    public static boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (required && !hasText(editText))
            return false;

        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }
}
