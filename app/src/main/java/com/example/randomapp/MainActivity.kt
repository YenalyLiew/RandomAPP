package com.example.randomapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.example.randomapp.databinding.ActivityMainBinding
import java.lang.StringBuilder

lateinit var binding: ActivityMainBinding
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
                linear.clearFocus()
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
                        enter.isEnabled = false
                        val builder = StringBuilder()
                        randomList.clear()
                        if (!sameNumCB.isChecked) {
                            while (randomList.size < ramdInt) {
                                randomList.add((startInt..endInt).random())
                                for (i in 0 until randomList.size - 1) {
                                    if (randomList[i] == randomList[randomList.size - 1]) {
                                        randomList.removeAt(randomList.size - 1)
                                        break
                                    }
                                }
                            }
                        } else {
                            while (randomList.size < ramdInt) {
                                randomList.add((startInt..endInt).random())
                            }
                        }
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
                randomList.clear()
                binding.start.setText("")
                binding.end.setText("")
                binding.ramd.setText("")
                binding.sameNumCB.isChecked = false
                binding.resultText.visibility = View.INVISIBLE
                binding.result.setText("")
                binding.linear.clearFocus()
                binding.enter.isEnabled = true
            }
        }
        return true
    }
}