package com.example.project_g09

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g09.databinding.ActivityMainBinding
import com.example.project_g09.databinding.ActivityWelcomeBackBinding
import com.example.project_g09.models.Lessons
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var binding1: ActivityWelcomeBackBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        val userName = sharedPreferences.getString("userName", null)
        if (userName == null) {
            setFirstLoginView()
        } else {
            setWelcomeBackView(userName)
        }

    }

    fun setFirstLoginView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnToLessonsList.setOnClickListener {
            val nameFromUI: String = binding.etName.text.toString()
            if (nameFromUI.isNotEmpty()) {
                val editor = sharedPreferences.edit()
                editor.putString("userName", nameFromUI)
                editor.apply()
                navigateToLessonsList()
            } else {
                val snackbar = Snackbar.make(binding.root, "Please Enter Your Name!", Snackbar.LENGTH_LONG)
                val view = snackbar.view
                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
                params.topMargin = 150
                view.layoutParams = params
                snackbar.show()
            }

        }
    }

    fun setWelcomeBackView(userName: String) {
        binding1 = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding1.root)
        binding1.nameFromUI.text = "Welcome back, ${userName}"
        binding1.btnToLessonsList2.setOnClickListener {
            navigateToLessonsList()
        }
        binding1.btnDeleteAll.setOnClickListener {
            deleteAllAndReset()
        }
        progress()
    }

    private fun progress(){
        val mySharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mySharedPreferences.getString("lessonsList", null)
        val type = object : TypeToken<MutableList<Lessons>>(){}.type
        if (json != null) {
            val lessonsListFromSharedPreferences: MutableList<Lessons> = gson.fromJson(json, type)
            val lessonsTotal = lessonsListFromSharedPreferences.size
            var lessonsCompleted = 0
            for (currLesson in lessonsListFromSharedPreferences) {
                if (currLesson.isComplete == true) {
                    lessonsCompleted++
                }
            }
            val lessonsRemaining = lessonsTotal - lessonsCompleted
            val completedPercentage = if (lessonsTotal > 0) {
                (lessonsCompleted.toDouble() / lessonsTotal.toDouble()) * 100
            } else {
                0.0
            }
            val progressOutput: String = """
            You've completed ${completedPercentage.toInt()}% of the course!
            Lessons completed: ${lessonsCompleted}
            Lessons remaining: ${lessonsRemaining}
            """.trimIndent()
            binding1.tvProgress.text = progressOutput
        }
    }

    private fun navigateToLessonsList(){
        val intent: Intent = Intent(this@MainActivity, LessonsList::class.java)
        startActivity(intent)
    }
    private fun deleteAllAndReset(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val editor2 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
        editor2.clear()
        editor2.apply()
        setFirstLoginView()
    }

}
