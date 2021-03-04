package ru.gb.androidone.donspb.arithmometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_NUMBER_LEN = 9;
    private static final String KEY_FIRST_NUMBER = "FIRST_NUMBER";
    private static final String KEY_OPERATION = "OPERATION";
    private static final String KEY_OP_ENTER = "OPER_ENTER";
    private static final String KEY_RES_SHOWING = "RES_SHOWING";
    private static final String KEY_TOP_SCREEN = "TOP_SCREEN";
    private static final String KEY_BOT_SCREEN = "BOT_SCREEN";
    private static final String KEY_CURRENT_NUMBER = "CURRENT";

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
    private StringBuilder enteredNumber;

    private String firstNumber;
    private String mathAction;
    private boolean actionSignEnter = false;
    private boolean resOnScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlsInit();
        initialSetup();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_FIRST_NUMBER, firstNumber);
        outState.putString(KEY_OPERATION, mathAction);
        outState.putBoolean(KEY_OP_ENTER, actionSignEnter);
        outState.putBoolean(KEY_RES_SHOWING, resOnScreen);
        outState.putString(KEY_TOP_SCREEN, topTextView.getText().toString());
        outState.putString(KEY_BOT_SCREEN, botTextView.getText().toString());
        outState.putString(KEY_CURRENT_NUMBER, enteredNumber.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstNumber = savedInstanceState.getString(KEY_FIRST_NUMBER);
        mathAction = savedInstanceState.getString(KEY_OPERATION);
        actionSignEnter = savedInstanceState.getBoolean(KEY_OP_ENTER);
        resOnScreen = savedInstanceState.getBoolean(KEY_RES_SHOWING);
        showOnScreen(savedInstanceState.getString(KEY_TOP_SCREEN),savedInstanceState.getString(KEY_BOT_SCREEN));
        enteredNumber = new StringBuilder(savedInstanceState.getString(KEY_CURRENT_NUMBER));
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
        enteredNumber = new StringBuilder("0");
        clearScreen();
        showOnScreen("", enteredNumber.toString());
        firstNumber = "";
        mathAction = "";
        resOnScreen = false;
        actionSignEnter = false;
    }

    private View.OnClickListener numbersOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (resOnScreen) enteredNumberClear();
            resOnScreen = false;
            actionSignEnter = false;
            switch (v.getId()) {
                case R.id.button_dot:
                    if (!enteredNumber.toString().contains(".")) enteredNumber.append(buttonDot.getText().toString());
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
                    Button pressedButton = (Button)v;
                    if (enteredNumber.toString().equals("0"))
                        enteredNumber.replace(0,1, pressedButton.getText().toString());
                    else if (enteredNumber.length() < MAX_NUMBER_LEN) enteredNumber.append(pressedButton.getText().toString());
                    break;
                default:
                    throw new RuntimeException("Unknown symbol");
            }
            showOnScreen("", enteredNumber.toString());
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
                    if (!actionSignEnter) {
                        int enteredNumberLength = enteredNumber.length();
                        if (enteredNumberLength > 1)
                            enteredNumber.delete(enteredNumberLength - 1, enteredNumberLength);
                        else
                            enteredNumberClear();
                    }
                    showOnScreen("", enteredNumber.toString());
                    break;
                case R.id.button_add:
                case R.id.button_div:
                case R.id.button_mult:
                case R.id.button_minus:
                    Button pressedButton = (Button)v;
                    operationClickedProcessor(pressedButton.getText().toString());
                    break;
                case R.id.button_equal:
                    calculationsDone();
                    break;
                default:
                    throw new RuntimeException("Unknown button");
            }
        }
    };

    private void operationClickedProcessor(String operation) {
        String topString = "", botString = "";

        if (!actionSignEnter) {
            actionSignEnter = true;
            if (firstNumber.isEmpty()) {
                firstNumber = enteredNumber.toString();
                enteredNumberClear();
                mathAction = operation;
                topString = firstNumber + " " + mathAction + " ";
                botString = enteredNumber.toString();
            }
            else {
                try {
                    firstNumber = getEquationResult();
                    mathAction = operation;
                    topString = enteredNumber.toString() + " " + mathAction + " ";
                    botString = firstNumber;
                    enteredNumberClear();
                } catch (ArithmeticException e) {
                    topString = enteredNumber.toString() + " " + mathAction + " ";
                    initialSetup();
                    botString = e.getMessage();
                }
            }
            showOnScreen(topString, botString);
        }
        else {
            mathAction = operation;
            changeActionOnScreen(mathAction);
        }
    }

    private void calculationsDone() {
        String tempResult = "";
        String eMessage = "";
        if (firstNumber.isEmpty()) tempResult = enteredNumber.toString();
        else {
            try {
                tempResult = getEquationResult();
            } catch (ArithmeticException e) {
                tempResult = "";
                eMessage = e.getMessage();
            }
        }
        clearScreen();
        firstNumber = "";
        if (!tempResult.isEmpty()) {
            enteredNumber.replace(0, enteredNumber.length(), tempResult);
            resOnScreen = true;
        }
        else {
            initialSetup();
            tempResult = eMessage;
        }
        showOnScreen("", tempResult);
        mathAction = "";
        actionSignEnter = false;
    }

    private void enteredNumberClear() {
        enteredNumber.replace(0, enteredNumber.length(), "0");
    }

    private String getEquationResult() {

        double a = Double.parseDouble(firstNumber);
        double b = Double.parseDouble(enteredNumber.toString());
        double res;
//        float a = Float.parseFloat(firstNumber);
//        float b = Float.parseFloat(enteredNumber.toString());
//        float res;
        switch (mathAction) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "x":
                res = a * b;
                break;
            case "÷":
                if (b != 0) res = a / b;
                else throw new ArithmeticException("ERR: DIV by ZERO");
                break;
            default:
                throw new RuntimeException("Unknownn action");
        }
        if (Double.isInfinite(res)) throw new ArithmeticException("ERR: TOO BIG");
        return cutZeros(res);
    }

    private String cutZeros(double res) {
//        DecimalFormat decimalFormat = new DecimalFormat("#.#######");
//        return decimalFormat.format(res);

//        int zeroCount = 0;
//        String string = String.format("%10.7e", res);
//        for (int i = string.length() - 1; i > 0; i--)
//            if (string.charAt(i) == '0') zeroCount++;
//            else {
//                if (string.charAt(i) == '.') zeroCount++;
//                break;
//            }
//        return string.substring(0,string.length() - zeroCount);

        BigDecimal bd = new BigDecimal(Double.toString(res));
        bd.setScale(7, RoundingMode.HALF_DOWN);
        return bd.toString();
    }

    private void changeActionOnScreen(String ch) {
        String topText = topTextView.getText().toString();
        clearScreen();
        showOnScreen(topText.substring(0, topText.length()-2) + ch + " ", enteredNumber.toString());
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