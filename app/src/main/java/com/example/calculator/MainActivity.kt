package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                expression += (it as Button).text.toString()
                display.text = expression
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { addOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { addOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { addOperator("×") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { addOperator("÷") }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            expression = ""
            display.text = ""
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (expression.isNotEmpty()) {
                expression = expression.dropLast(1)
                display.text = expression
            }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            calculateResult()
        }
    }

    private fun addOperator(op: String) {
        if (expression.isNotEmpty()) {
            expression += op
            display.text = expression
        }
    }

    private fun calculateResult() {
        try {
            val result = when {
                expression.contains("+") -> {
                    val parts = expression.split("+")
                    parts[0].toDouble() + parts[1].toDouble()
                }
                expression.contains("-") -> {
                    val parts = expression.split("-")
                    parts[0].toDouble() - parts[1].toDouble()
                }
                expression.contains("×") -> {
                    val parts = expression.split("×")
                    parts[0].toDouble() * parts[1].toDouble()
                }
                expression.contains("÷") -> {
                    val parts = expression.split("÷")
                    if (parts[1].toDouble() == 0.0) {
                        display.text = "Cannot divide by zero"
                        return
                    }
                    parts[0].toDouble() / parts[1].toDouble()
                }
                else -> expression.toDouble()
            }

            expression = result.toString()
            display.text = expression

        } catch (e: Exception) {
            display.text = "Error"
            expression = ""
        }
    }
}