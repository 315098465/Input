package com.pvirtech.licenseplate.mylibrary;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class InputPlateActivity extends AppCompatActivity {
    private KeyboardView keyboardView;
    private Keyboard k1;// 省份简称键盘
    private Keyboard k2;// 数字字母键盘

    private String provinceShort[];
    private String letterAndDigit[];
    private int currentEditText = 0;//默认当前光标在第一个EditText
    private TextView[] tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_plate);


        tvList = new TextView[7];
        tvList[0] = (TextView) findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) findViewById(R.id.tv_pass6);
        tvList[6] = (TextView) findViewById(R.id.tv_pass7);


        k1 = new Keyboard(this, R.xml.province_short_keyboard_big);
        k2 = new Keyboard(this, R.xml.lettersanddigit_keyboard_big);
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
        keyboardView.setPreviewEnabled(true);
        //设置键盘按键监听器
        keyboardView.setOnKeyboardActionListener(listener);
        provinceShort = new String[]{"京", "津", "冀", "鲁", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘", "粤", "桂", "渝", "川",
                "贵", "云", "藏", "陕", "甘", "青", "琼", "新", "港", "澳", "台", "宁"};

        letterAndDigit = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S",
                "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        showKeyboard();
    }


    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode == 112) { //xml中定义的删除键值为112
                currentEditText--;
                tvList[currentEditText].setText("");//将当前EditText置为""并currentEditText-1
                if (currentEditText < 1) {
                    //切换为省份简称键盘
                    keyboardView.setKeyboard(k1);
                }
                if (currentEditText < 0) {
                    currentEditText = 0;
                }
            }  else { //其它字符按键
                if (currentEditText == 0) {
                    //如果currentEditText==0代表当前为省份键盘,
                    // 按下一个按键后,设置相应的EditText的值
                    // 然后切换为字母数字键盘
                    //currentEditText+1
                    tvList[0].setText(provinceShort[primaryCode]);
                    currentEditText = 1;
                    //切换为字母数字键盘
                    keyboardView.setKeyboard(k2);
                } else {
                    //第二位必须大写字母
                    if (currentEditText == 1 && !letterAndDigit[primaryCode].matches("[A-Z]{1}")) {
                        return;
                    }
                    if (currentEditText >= 6) {
                        currentEditText = 6;
                    }
                    tvList[currentEditText].setText(letterAndDigit[primaryCode]);
                    if (currentEditText == 6) {
                        Intent intent = new Intent();
                        String license = "";
                        for (int i = 0; i < 7; i++) {
                            license += tvList[i].getText().toString();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("data", license);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    currentEditText++;
                }
            }
        }
    };

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }
}
