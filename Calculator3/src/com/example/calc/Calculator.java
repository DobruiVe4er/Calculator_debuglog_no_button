package com.example.calc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.MotionEvent;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends Activity implements View.OnClickListener, TextWatcher, View.OnLongClickListener, OnTouchListener {
    EditText etResult;
    Button btnPlus, btnMinus, btnMultiply, btnDivide, btnClear, btnAbs, btnDiv, btnMod, btnStepen, button0, button1,
            button2, button3, button4, button5, button6, button7, button8, button9, btnCalculate;
    TextView tvCurrentMethod, tvLastValue;

    boolean debugLogEnabled = false;
    boolean equalsButtonLongPressed = false;

    int equalsButtonPressCount = 0;
    long lastButtonClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        etResult = (EditText) findViewById(R.id.etResult);
        etResult.addTextChangedListener(this);

        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnAbs = (Button) findViewById(R.id.btnAbs);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnMod = (Button) findViewById(R.id.btnMod);
        btnStepen = (Button) findViewById(R.id.btnStepen);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);

        tvCurrentMethod = (TextView) findViewById(R.id.tvCurrentMethod);
        tvLastValue = (TextView) findViewById(R.id.tvLastValue);

        tvCurrentMethod.setText("");
        tvLastValue.setText("");

        btnCalculate.setOnLongClickListener(this);

        // Добавление обработчика onTouch для каждого элемента
        btnPlus.setOnTouchListener(this);
        btnMinus.setOnTouchListener(this);
        btnMultiply.setOnTouchListener(this);
        btnDivide.setOnTouchListener(this);
        btnClear.setOnTouchListener(this);
        btnAbs.setOnTouchListener(this);
        btnDiv.setOnTouchListener(this);
        btnMod.setOnTouchListener(this);
        btnStepen.setOnTouchListener(this);
        button0.setOnTouchListener(this);
        button1.setOnTouchListener(this);
        button2.setOnTouchListener(this);
        button3.setOnTouchListener(this);
        button4.setOnTouchListener(this);
        button5.setOnTouchListener(this);
        button6.setOnTouchListener(this);
        button7.setOnTouchListener(this);
        button8.setOnTouchListener(this);
        button9.setOnTouchListener(this);
        btnCalculate.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlus:
            case R.id.btnMinus:
            case R.id.btnMultiply:
            case R.id.btnDivide:
            case R.id.btnStepen:
                etResult.append(((Button) view).getText());
                break;
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                etResult.append(((Button) view).getText());
                break;
            case R.id.btnClear:
                etResult.setText("");
                break;
            case R.id.btnAbs:
                if (etResult.getText().length() > 0) {
                    double double_num1 = Double.parseDouble(etResult.getText().toString());
                    double_num1 = Math.abs(double_num1);
                    etResult.setText(String.valueOf(double_num1));
                }
                break;
            case R.id.btnDiv:
                try {
                    if (etResult.getText().length() > 0) {
                        int int_num1 = Integer.parseInt(etResult.getText().toString());
                        int int_num2 = Integer.parseInt(etResult.getText().toString());
                        if (int_num2 == 0) {
                            throw new CalculatorException("Division by zero is not allowed");
                        }
                        int int_result = int_num1 / int_num2;
                        etResult.setText(String.valueOf(int_result));
                    }
                } catch (CalculatorException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMod:
                // Обработка кнопки btnMod
                break;
            case R.id.btnCalculate:
                equalsButtonPressCount++;
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastButtonClickTime < 500 && equalsButtonPressCount >= 5) {
                    toggleDebugLog(true);
                    equalsButtonPressCount = 0;
                }
                lastButtonClickTime = currentTime;
                break;
        }

        // Вывод текущего метода
        tvCurrentMethod.setText("Текущий метод: " + getResources().getResourceEntryName(view.getId()));
        // Вывод последнего значения кнопки
        tvLastValue.setText("Последнее значение: " + ((Button) view).getText());

        if (debugLogEnabled) {
            tvCurrentMethod.setVisibility(View.VISIBLE);
            tvLastValue.setVisibility(View.VISIBLE);
        } else {
            tvCurrentMethod.setVisibility(View.GONE);
            tvLastValue.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public boolean onLongClick(View v) {
        equalsButtonLongPressed = true;
        toggleDebugLog(false);
        return true;
    }

    private void toggleDebugLog(boolean enableDebugLog) {
        if (enableDebugLog && !debugLogEnabled) {
            debugLogEnabled = true;
        } else if (!enableDebugLog && equalsButtonLongPressed) {
            debugLogEnabled = false;
            equalsButtonLongPressed = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && equalsButtonLongPressed) {
            toggleDebugLog(false);
            equalsButtonLongPressed = false;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && equalsButtonLongPressed) {
            toggleDebugLog(false);
            equalsButtonLongPressed = false;
        }
        return super.onTouchEvent(event);
    }

    class CalculatorException extends Exception {
        CalculatorException(String message) {
            super(message);
        }
    }
}
