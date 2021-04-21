package com.example.food_app.auth;

import android.text.Editable;
import android.text.TextWatcher;

public class PasswordValidator  implements TextWatcher {


    private boolean mIsValid = false;
    public boolean isValid() {
        return mIsValid;
    }
    /**
     *
     * @param Password
     */
    public static boolean isValidPassword(CharSequence Password) {
        return Password != null &&
                Password.length() > 8;
    }


    @Override
    final public void afterTextChanged(Editable editableText) {
        mIsValid = isValidPassword(editableText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


//    @Override
//    final public void beforeTextChanged(CharSequence s, int start,
//                                        int count, int after) {/*No-op*/}
//    @Override
//    final public void onTextChanged(CharSequence s, int start, int
//            before, int count) {/*No-op*/}
}
