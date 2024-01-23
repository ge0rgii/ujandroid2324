package com.hfad.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.objecthunter.exp4j.ExpressionBuilder
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result: TextView = findViewById(R.id.result) as TextView
        val operation: TextView = findViewById(R.id.operation) as TextView

        val bPow = findViewById<TextView>(R.id.b_pow)
        val bLn = findViewById<TextView>(R.id.b_ln)
        val bLeftb = findViewById<TextView>(R.id.b_leftb)
        val bRightb = findViewById<TextView>(R.id.b_rightb)
        val bAC = findViewById<TextView>(R.id.b_AC)
        val bBack = findViewById<TextView>(R.id.b_back)
        val bPerc = findViewById<TextView>(R.id.b_perc)
        val bDiv = findViewById<TextView>(R.id.b_div)
        val b7 = findViewById<TextView>(R.id.b_7)
        val b8 = findViewById<TextView>(R.id.b_8)
        val b9 = findViewById<TextView>(R.id.b_9)
        val bMult = findViewById<TextView>(R.id.b_mult)
        val b4 = findViewById<TextView>(R.id.b_4)
        val b5 = findViewById<TextView>(R.id.b_5)
        val b6 = findViewById<TextView>(R.id.b_6)
        val bMinus = findViewById<TextView>(R.id.b_minus)
        val b1 = findViewById<TextView>(R.id.b_1)
        val b2 = findViewById<TextView>(R.id.b_2)
        val b3 = findViewById<TextView>(R.id.b_3)
        val bPlus = findViewById<TextView>(R.id.b_plus)
        val bPoint = findViewById<TextView>(R.id.b_point)
        val b0 = findViewById<TextView>(R.id.b_0)
        val bEqual = findViewById<TextView>(R.id.b_equal)

        bPow.setOnClickListener { operation.append("^") }
        bLn.setOnClickListener { operation.append("ln(") }
        bLeftb.setOnClickListener { operation.append("(") }
        bRightb.setOnClickListener { operation.append(")") }
        bPerc.setOnClickListener { operation.append("%") }
        bDiv.setOnClickListener { operation.append("÷") }
        b7.setOnClickListener { operation.append("7") }
        b8.setOnClickListener { operation.append("8") }
        b9.setOnClickListener { operation.append("9") }
        bMult.setOnClickListener { operation.append("×") }
        b4.setOnClickListener { operation.append("4") }
        b5.setOnClickListener { operation.append("5") }
        b6.setOnClickListener { operation.append("6") }
        bMinus.setOnClickListener { operation.append("-") }
        b1.setOnClickListener { operation.append("1") }
        b2.setOnClickListener { operation.append("2") }
        b3.setOnClickListener { operation.append("3") }
        bPlus.setOnClickListener { operation.append("+") }
        bPoint.setOnClickListener { operation.append(".") }
        b0.setOnClickListener { operation.append("0") }

        bBack.setOnClickListener {
            val s = operation.text.toString()
            if (s != "") {
                operation.text = s.substring(0, s.length - 1)
            }
        }

        bAC.setOnClickListener {

            operation.text = ""
            result.text = ""

        }

        result.setOnClickListener {
            val restext = result.text.toString()
            if (restext != "Error" && restext != ""){
                operation.text = restext
                result.text = ""
            }
        }

        fun addClosingBrackets(expression: String): String {
            var openBrackets = 0
            var closeBrackets = 0

            for (char in expression) {
                if (char == '(') {
                    openBrackets++
                } else if (char == ')') {
                    closeBrackets++
                }
            }

            val missingBrackets = openBrackets - closeBrackets
            return if (missingBrackets > 0) {
                expression + ")".repeat(missingBrackets)
            } else {
                expression
            }
        }

        fun truncateSmallDecimal(value: Double, threshold: Double = 1e-10): String {
            return if (Math.abs(value - value.toLong()) < threshold) {
                value.toLong().toString()
            } else {
                value.toString()
            }
        }

        bEqual.setOnClickListener {
            var optext = operation.text.toString() // Save expression to string
            if (optext != "") {
                try {
                    optext = optext.replace('÷', '/') // Replace ÷ with /
                    optext = optext.replace('×', '*') // Replace × with *
                    optext = optext.replace("ln", "log") // Replace ln with log
                    optext = optext.replace("%", "/100") // Replace % with /100
                    optext = addClosingBrackets(optext) // Add closing brackets
                    val expr = ExpressionBuilder(optext).build() // Build expression
                    val res = expr.evaluate() // Evaluate expression
                    result.text = truncateSmallDecimal(res) // Show result
                } catch (e: Exception) { // If error
                    result.text = "Error" // Show error
                }
            }
        }

    }
}