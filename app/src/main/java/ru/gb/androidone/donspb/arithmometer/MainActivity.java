package ru.gb.androidone.donspb.arithmometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String KEY_FIRST_NUMBER = "FIRST_NUMBER";
    private static final String KEY_OPERATION = "OPERATION";
    private static final String KEY_OP_ENTER = "OPER_ENTER";
    private static final String KEY_RES_SHOWING = "RES_SHOWING";
    private static final String KEY_TOP_SCREEN = "TOP_SCREEN";
    private static final String KEY_BOT_SCREEN = "BOT_SCREEN";
    private static final String KEY_CURRENT_NUMBER = "CURRENT";

    private static final String settings = "settings.xml";
    private static final String darkSet = "Dark";

    private TextView topTextView;
    private TextView botTextView;
    private Button buttonZero, buttonOne, buttonTwo,
            buttonThree, buttonFour, buttonFive,
            buttonSix, buttonSeven, buttonEight,
            buttonNine;
    private Button buttonC, buttonBack;
    private Button buttonDiv, buttonMult,
            buttonDec, buttonAdd;
    private Button buttonEqauls;
    private Button buttonDot;
    private Button buttonSettings;

    private boolean actionSignEnter = false;
    private boolean resOnScreen = false;

    Calcore calcore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeSetter();
        setContentView(R.layout.activity_main);
        controlsInit();
        initialSetup();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_FIRST_NUMBER, calcore.getPreviousNumber());
        outState.putString(KEY_OPERATION, calcore.getOperation());
        outState.putBoolean(KEY_OP_ENTER, actionSignEnter);
        outState.putBoolean(KEY_RES_SHOWING, resOnScreen);
        outState.putString(KEY_TOP_SCREEN, topTextView.getText().toString());
        outState.putString(KEY_BOT_SCREEN, botTextView.getText().toString());
        outState.putString(KEY_CURRENT_NUMBER, calcore.getCurrentNumber());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calcore = new Calcore(savedInstanceState.getString(KEY_FIRST_NUMBER),
                savedInstanceState.getString(KEY_CURRENT_NUMBER),
                savedInstanceState.getString(KEY_OPERATION));
        actionSignEnter = savedInstanceState.getBoolean(KEY_OP_ENTER);
        resOnScreen = savedInstanceState.getBoolean(KEY_RES_SHOWING);
        showOnScreen(savedInstanceState.getString(KEY_TOP_SCREEN),savedInstanceState.getString(KEY_BOT_SCREEN));
    }

    private void controlsInit() {
        topTextView = findViewById(R.id.topScreen);
        botTextView = findViewById(R.id.botScreen);
        buttonZero = findViewById(R.id.button_0);
        buttonOne = findViewById(R.id.button_1);
        buttonTwo = findViewById(R.id.button_2);
        buttonThree  = findViewById(R.id.button_3);
        buttonFour  = findViewById(R.id.button_4);
        buttonFive  = findViewById(R.id.button_5);
        buttonSix  = findViewById(R.id.button_6);
        buttonSeven = findViewById(R.id.button_7);
        buttonEight = findViewById(R.id.button_8);
        buttonNine = findViewById(R.id.button_9);
        buttonC = findViewById(R.id.button_ce);
        buttonBack = findViewById(R.id.button_back);
        buttonDiv = findViewById(R.id.button_div);
        buttonMult = findViewById(R.id.button_mult);
        buttonDec = findViewById(R.id.button_minus);
        buttonAdd = findViewById(R.id.button_add);
        buttonEqauls = findViewById(R.id.button_equal);
        buttonDot = findViewById(R.id.button_dot);

        buttonSettings = findViewById(R.id.main_set_button);
        buttonSettings.setOnClickListener(activityChangeListener);

        buttonZero.setOnClickListener(numbersOnClickListener);
        buttonOne.setOnClickListener(numbersOnClickListener);
        buttonTwo.setOnClickListener(numbersOnClickListener);
        buttonThree.setOnClickListener(numbersOnClickListener);
        buttonFour.setOnClickListener(numbersOnClickListener);
        buttonFive.setOnClickListener(numbersOnClickListener);
        buttonSix.setOnClickListener(numbersOnClickListener);
        buttonSeven.setOnClickListener(numbersOnClickListener);
        buttonEight.setOnClickListener(numbersOnClickListener);
        buttonNine.setOnClickListener(numbersOnClickListener);
        buttonDot.setOnClickListener(numbersOnClickListener);

        buttonC.setOnClickListener(actionOnClickListener);
        buttonBack.setOnClickListener(actionOnClickListener);
        buttonDiv.setOnClickListener(actionOnClickListener);
        buttonMult.setOnClickListener(actionOnClickListener);
        buttonDec.setOnClickListener(actionOnClickListener);
        buttonAdd.setOnClickListener(actionOnClickListener);
        buttonEqauls.setOnClickListener(actionOnClickListener);
    }

    private void initialSetup() {
        calcore = new Calcore();
        clearScreen();
        showOnScreen("", calcore.getCurrentNumber());
        resOnScreen = false;
        actionSignEnter = false;
    }

    private void themeSetter() {
        boolean isDark = getSharedPreferences(settings,MODE_PRIVATE).getBoolean(darkSet, false);
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private View.OnClickListener activityChangeListener = (View v) -> {
            Intent windowSettings = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(windowSettings);
    };

    private View.OnClickListener numbersOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionSignEnter = false;
            switch (v.getId()) {
                case R.id.button_dot:
                    if (!resOnScreen) calcore.addDot();
                    break;
                case R.id.button_0:
                case R.id.button_1:
                case R.id.button_2:
                case R.id.button_3:
                case R.id.button_4:
                case R.id.button_5:
                case R.id.button_6:
                case R.id.button_7:
                case R.id.button_8:
                case R.id.button_9:
                    if (resOnScreen) clearScreen();
                    Button pressedButton = (Button)v;
                    calcore.addDigit(pressedButton.getText().toString(), resOnScreen);
                    resOnScreen = false;
                    break;
                default:
                    throw new RuntimeException("Unknown symbol");
            }
            showOnScreen("", calcore.getCurrentNumber());
        }
    };

    private View.OnClickListener actionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_ce:
                    initialSetup();
                    break;
                case R.id.button_back:
                    if (!actionSignEnter) calcore.deleteDigit();
                    showOnScreen("", calcore.getCurrentNumber());
                    break;
                case R.id.button_add:
                case R.id.button_div:
                case R.id.button_mult:
                case R.id.button_minus:
                    Button pressedButton = (Button)v;
                    operationClickedProcessor(pressedButton.getText().toString());
                    break;
                case R.id.button_equal:
                    if (!resOnScreen) calculationsDone();
                    break;
                default:
                    throw new RuntimeException("Unknown button");
            }
        }
    };

    private void operationClickedProcessor(String operation) {
        String topString = "", botString = "";

        if (!actionSignEnter) {
            if (calcore.getPreviousNumber().isEmpty()) clearScreen();
            topString = calcore.getCurrentNumber() + " " + operation + " ";
            actionSignEnter = true;
            try {
                botString = calcore.addOperation(operation);
            } catch (ArithmeticException e) {
                botString = e.getMessage();
            }
            showOnScreen(topString, botString);
        }
        else {
            calcore.setOperation(operation);
            String topText = topTextView.getText().toString();
            clearScreen();
            showOnScreen(topText.substring(0, topText.length()-2) + operation + " ", calcore.getCurrentNumber());
        }
        resOnScreen = false;
    }

    private void calculationsDone() {
        String oldCurrent;
        String tempResult;

        if (calcore.getPreviousNumber().isEmpty()) showOnScreen("", calcore.getCurrentNumber());
        else {
            oldCurrent = calcore.getCurrentNumber();
            try {
                tempResult = calcore.getResult();
            } catch (ArithmeticException e) {
                tempResult = e.getMessage();
            }
            showOnScreen(oldCurrent, tempResult);
        }

        resOnScreen = true;
        actionSignEnter = false;
    }

    private void showOnScreen(String topScreenData, String botScreenData) {
        topTextView.setText(topTextView.getText().toString() + topScreenData);
        botTextView.setText(botScreenData);
    }

    private void clearScreen() {
        topTextView.setText("");
        botTextView.setText("");
    }

}

