package com.example.project_g09

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g09.databinding.ActivityLessonDetailsBinding
import com.example.project_g09.models.Lessons
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonDetails : AppCompatActivity() {
    private lateinit var binding: ActivityLessonDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var lessonsList:MutableList<Lessons> = mutableListOf()
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lesson: Lessons = intent.getSerializableExtra("LESSON_DETAIL") as Lessons
        val id:Int = lesson.id.toInt()-1

        binding.tvNumber.text = lesson.id+". "
        binding.tvName.text = lesson.name
        binding.tvLength.text = "Length: ${lesson.length}"
        binding.tvDescription.text = lesson.description

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        loadMyPrefsFromSharedPreferences()

        binding.btnWatchLesson.setOnClickListener {
            webView = binding.webView
            webView.loadUrl(lesson.URL)
        }

        binding.btnMarkComplete.setOnClickListener {
            lessonsList[id].isComplete = true
            saveMyPrefsToSharedPreferences()
            binding.btnMarkComplete.text = "Completed"
        }
        if (lesson.isComplete == true) {
            binding.btnMarkComplete.text = "Completed"
        }
    }
    private fun loadMyPrefsFromSharedPreferences() {
        val json = sharedPreferences.getString("lessonsList", null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Lessons>>() {}.type
        if (json != null) {
            lessonsList.clear()
            lessonsList.addAll(gson.fromJson(json, type))
        }
    }

    private fun saveMyPrefsToSharedPreferences() {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(lessonsList)
        editor.putString("lessonsList", json)
        editor.apply()
    }
}