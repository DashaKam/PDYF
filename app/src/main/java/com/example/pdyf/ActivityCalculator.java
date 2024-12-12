package com.example.pdyf;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityCalculator extends AppCompatActivity {
    private EditText editTextResult;
    private String operator;
    private double num1 = Double.NaN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        editTextResult = findViewById(R.id.editTextResult);
        setButtonListeners();
    }

    private void setButtonListeners() {
        findViewById(R.id.button1).setOnClickListener(v -> editTextResult.append("1"));
        findViewById(R.id.button2).setOnClickListener(v -> editTextResult.append("2"));
        findViewById(R.id.button3).setOnClickListener(v -> editTextResult.append("3"));
        findViewById(R.id.button4).setOnClickListener(v -> editTextResult.append("4"));
        findViewById(R.id.button5).setOnClickListener(v -> editTextResult.append("5"));
        findViewById(R.id.button6).setOnClickListener(v -> editTextResult.append("6"));
        findViewById(R.id.button7).setOnClickListener(v -> editTextResult.append("7"));
        findViewById(R.id.button8).setOnClickListener(v -> editTextResult.append("8"));
        findViewById(R.id.button9).setOnClickListener(v -> editTextResult.append("9"));
        findViewById(R.id.button0).setOnClickListener(v -> editTextResult.append("0"));

        findViewById(R.id.buttonAdd).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> handleOperator("/"));

        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonEquals).setOnClickListener(v -> calculate());
    }

    private void handleOperator(String op) {
        num1 = Double.parseDouble(editTextResult.getText().toString());
        operator = op;
        editTextResult.setText("");
    }

    private void calculate() {
        double num2 = Double.parseDouble(editTextResult.getText().toString());
        double result;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                return;
        }

        editTextResult.setText(String.valueOf(result));
        num1 = Double.NaN; // Сброс значения
        operator = null; // Сброс оператора
    }

    private void clear() {
        editTextResult.setText("");
        num1 = Double.NaN;
        operator = null;
    }
    public void goBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}