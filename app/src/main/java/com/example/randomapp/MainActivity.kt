package com.example.randomapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import com.example.randomapp.databinding.ActivityMainBinding
import java.lang.StringBuilder

lateinit var binding: ActivityMainBinding
private val list = mutableListOf<Int>()
private val randomList = mutableListOf<Int>()

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        binding.apply {
            enter.setOnClickListener {
                enter.isEnabled = false
                linear.clearFocus()
                list.clear()
                randomList.clear()
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
                        val builder = StringBuilder()
                        for (i in startInt..endInt) {
                            list.add(i)
                        }
                        for (i in 0 until ramdInt) {
                            val ramdNum = (0 until list.size).random()
                            randomList.add(list[ramdNum])
                            if (!binding.sameNumCB.isChecked) {
                                list.removeAt(ramdNum)
                            }
                        }
                        list.clear()
                        randomList.sort()
                        for (i in 0 until randomList.size) {
                            builder.append(randomList[i])
                            if (i < randomList.size - 1)
                                builder.append("\n")
                        }
                        enter.isEnabled = true
                        resultText.visibility = View.VISIBLE
                        result.setText(builder.toString())
                    }
                }
            }
            navView.setCheckedItem(R.id.randNum)
            navView.setNavigationItemSelectedListener {
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.random_num_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.resetAll -> {
                list.clear()
                randomList.clear()
                binding.start.setText("")
                binding.end.setText("")
                binding.ramd.setText("")
                binding.sameNumCB.isChecked = false
                binding.resultText.visibility = View.INVISIBLE
                binding.result.setText("")
            }
        }
        return true
    }
}