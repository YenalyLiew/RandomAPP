package com.example.randomapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val start: TextInputEditText = findViewById(R.id.start)
        val end: TextInputEditText = findViewById(R.id.end)
        val ramd: TextInputEditText = findViewById(R.id.ramd)
        val enter: Button = findViewById(R.id.enter)
        val result: TextView = findViewById(R.id.result)
        val resultText: TextView = findViewById(R.id.resultText)
        val list = mutableListOf<Int>()
        enter.setOnClickListener {
            if (start.text.toString() != "" && end.text.toString() != "" && ramd.text.toString() != "") {
                val startInt = start.text.toString().toInt()
                val endInt = end.text.toString().toInt()
                val ramdInt = ramd.text.toString().toInt()
                if (startInt > endInt) {
                    start.error = "首位数字比末位数字大"
                    end.error = "首位数字比末位数字大"
                }
                if (endInt - startInt < ramdInt && startInt < endInt) {
                    ramd.error = "取值个数太大"
                }
                if (endInt - startInt >= ramdInt && startInt < endInt) {
                    resultText.visibility = View.VISIBLE
                    list.clear()
                    while (list.size < ramdInt) {
                        list.add((startInt..endInt).random())
                        for (i in 0 until list.size - 1) {
                            if (list[i] == list[list.size - 1]) {
                                list.removeAt(list.size - 1)
                                break
                            }
                        }
                    }
                    list.sort()
                    val builder = StringBuilder()
                    for (i in 0 until list.size) {
                        builder.append(list[i])
                        builder.append("\n")
                    }
                    result.text = builder.toString()
                }
            }
        }
    }
}