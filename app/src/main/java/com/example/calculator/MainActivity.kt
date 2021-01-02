package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val operatorsList = listOf("+", "-", "x", "/", "%")
    private var digitOnScreen = StringBuilder()
    private var result: Float? = null
    private var leftHandDigit: Float? = null
    private var rightHandDigit: Float? = null
    private var operator: String = ""
    private var tmp = StringBuilder()
    private var currOperator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseDigits()
        operationInitialize()
        functionInitializer()
    }

    private fun initialiseDigits() {
        zeroTextView.setOnClickListener {
            digitAppend("0")
        }
        oneTextView.setOnClickListener {
            digitAppend("1")
        }
        twoTextView.setOnClickListener {
            digitAppend("2")
        }
        threeTextView.setOnClickListener {
            digitAppend("3")
        }
        fourTextView.setOnClickListener {
            digitAppend("4")
        }
        fiveTextView.setOnClickListener {
            digitAppend("5")
        }
        sixTextView.setOnClickListener {
            digitAppend("6")
        }
        sevenTextView.setOnClickListener {
            digitAppend("7")
        }
        eightTextView.setOnClickListener {
            digitAppend("8")
        }
        nineTextView.setOnClickListener {
            digitAppend("9")
        }
        pointTextView.setOnClickListener {
            if (result != null) {
                if (operator != "") {
                    if (tmp.toString() == "") {
                        digitAppend("0")
                    }
                } else {
                    clearEverything()
                    digitAppend("0")
                }
            } else if (digitOnScreen.toString() == "") {
                digitAppend("0")
            } else if (operator != "") {
                if (tmp.toString() == "") {
                    digitAppend("0")
                }
            }
            digitAppend(".")
        }
    }

    private fun operationInitialize() {
        plusTextView.setOnClickListener {
            setOperatorSymbol("+")
        }
        minusTextView.setOnClickListener {
            setOperatorSymbol("-")
        }
        multiplyTextView.setOnClickListener {
            setOperatorSymbol("x")
        }
        divideTextView.setOnClickListener {
            setOperatorSymbol("/")
        }
        percentTextView.setOnClickListener {
            setOperatorSymbol("%")
        }
    }

    private fun functionInitializer() {
        ACtextView.setOnClickListener {
            clearEverything()
        }
        deleteTextView.setOnClickListener {
            clearDigit()
        }
        equalTextView.setOnClickListener {
            if (leftHandDigit == null) {
                clearEverything()
            } else if (tmp.toString() == "") {
                tmp.append("0.0")
                calculate()
            } else if (result == null && leftHandDigit != null && tmp.toString() != "") {
                calculate()
            } else calculate()
        }
    }

    private fun setOperatorSymbol(sign: String) {
        currOperator = sign
        if (digitOnScreen.toString() == "" && result == null) {
            leftHandDigit = 0.0f
            digitAppend("0")
            operator = currOperator
        } else if (result == null && leftHandDigit != null && tmp.toString() != "") {
            equalTextView.performClick()
            leftHandDigit = result
            result = null
            digitAppend(leftHandDigit.toString())
            operator = currOperator

        } else {
            if (result == null) {
                val check = checkPreviousOperator()
                val length = digitOnScreen.length
                if (check) {
                    digitOnScreen.deleteCharAt(length - 1)
                }
                leftHandDigit = digitOnScreen.toString().toFloat()
            } else {
                leftHandDigit = result
                result = null
                digitAppend(leftHandDigit.toString())
            }
            operator = currOperator
        }
        digitAppend(currOperator)
        tmp.clear()
    }

    private fun calculate() {
        rightHandDigit = tmp.toString().toFloat()
        digitOnScreen.clear()
        when (operator) {
            "+" -> {
                result = (leftHandDigit!! + rightHandDigit!!)
            }
            "-" -> {
                result = (leftHandDigit!! - rightHandDigit!!)
            }
            "/" -> {
                result = (leftHandDigit!! / rightHandDigit!!)
            }
            "x" -> {
                result = (leftHandDigit!! * rightHandDigit!!)
            }
            "%" -> {
                result = ((leftHandDigit!! * rightHandDigit!!) / 100)
            }
        }
        resultView.text = result.toString()
        operator = ""
    }

    private fun clearEverything() {
        digitOnScreen.clear()
        result = null
        leftHandDigit = null
        rightHandDigit = null
        operator = ""
        resultView.text = "0"
    }

    private fun clearDigit() {
        val length = digitOnScreen.length
        if (length == 0 || length == 1) {
            clearEverything()
        } else {
            val tmpLength = tmp.length
            digitOnScreen.deleteCharAt(length - 1)
            if (tmpLength > 0) {
                tmp.deleteCharAt(tmpLength - 1)
            }
            resultView.text = digitOnScreen.toString()
        }
    }

    private fun checkPreviousOperator(): Boolean {
        val length = digitOnScreen.length
        val charAtEnd = digitOnScreen[length - 1].toString()
        return charAtEnd in operatorsList
    }

    //For Digit and Operator append
    private fun digitAppend(digit: String) {
        if (digit !in operatorsList) {
            if (result != null) {
                clearEverything()
            }
        }
        digitOnScreen.append(digit)
        tmp.append(digit)
        resultView.text = digitOnScreen.toString()
    }
}