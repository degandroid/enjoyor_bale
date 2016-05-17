package com.enjoyor.healthhouse.custom;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by YuanYuan on 2016/5/9.
 */
public class Watcher implements TextWatcher {
    private EditText editText;
    ImageView img;

    public Watcher(EditText id, ImageView img) {
        this.editText = id;
        this.img = img;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            img.setVisibility(View.GONE);
        } else
            img.setVisibility(View.VISIBLE);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
