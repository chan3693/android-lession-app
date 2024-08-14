package com.example.project_g09

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g09.adapters.LessonsListAdapter
import com.example.project_g09.databinding.ActivityLessonsListBinding
import com.example.project_g09.models.Lessons
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonsList : AppCompatActivity() {
    lateinit var binding: ActivityLessonsListBinding
    lateinit var myAdapter: LessonsListAdapter
    lateinit var sharedPreferences: SharedPreferences
    val lessonsList:MutableList<Lessons> = mutableListOf()
    val toLessonDetails = {
        rowNumber:Int ->
        val intent: Intent = Intent(this@LessonsList, LessonDetails::class.java)

        intent.putExtra("LESSON_DETAIL",lessonsList[rowNumber])
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        myAdapter = LessonsListAdapter(lessonsList, toLessonDetails)
        binding.rv.adapter = myAdapter
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        loadLessonsFromSharedPreferences()
        myAdapter.notifyDataSetChanged()
    }

    private fun loadLessonsFromSharedPreferences(){
        val gson = Gson()
        val json = sharedPreferences.getString("lessonsList", null)
        val type = object : TypeToken<MutableList<Lessons>>(){}.type
        val lessonsListFromSharedPreferences: MutableList<Lessons> = if(json != null){
            gson.fromJson(json, type)
        } else {
            defaultLessonsFromSharedPreferences()
        }
        lessonsList.clear()
        lessonsList.addAll(lessonsListFromSharedPreferences)
    }

    private fun defaultLessonsFromSharedPreferences():MutableList<Lessons>{
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val defaultLessonsList: MutableList<Lessons> = mutableListOf(
            Lessons(
                "1",
                "Introduction to the Course",
                "12 min",
                "This lesson serves as your navigational guide through the course, outlining the structure and objectives. We'll discuss what you can expect to achieve by the end of this journey and how to make the most out of the resources provided to enhance your programming skills.",
                false,
                "https://youtu.be/zOjov-2OZ0E?si=QLkYZsyB_XKCM180"
            ),
            Lessons(
                "2",
                "What is Javascript?",
                "30 min",
                "Dive into the essentials of JavaScript, exploring its history, significance in modern web development, and why it's a cornerstone of front-end technology. This lesson introduces basic syntax and how to embed JavaScript code into web pages, setting the foundation for dynamic and interactive web development.",
                false,
                "https://youtu.be/upDLs1sn7g4?si=iLqfdq5GGWRnHcjc"
            ),
            Lessons(
                "3",
                "Variables and Conditionals",
                "1 hr 20 min",
                "This lesson delves into variables and conditional statements in JavaScript, essential tools for controlling the flow of your programs. Learn how to declare variables, manipulate data, and use conditional logic to make decisions. Through practical examples, you'll see how these concepts enable complex functionality in web applications.",
                false,
                "https://www.youtube.com/watch?v=ZLk-c08-Kxw&list=PLbsvRhEyGkKdZ2BpRn2FdjIgCy1jLaZt7"
            ),
            Lessons(
                "4",
                "Loops",
                "38 min",
                "Loops are fundamental to programming, allowing you to perform repetitive tasks efficiently. This lesson covers the different types of loops available in JavaScript, including for, while, and do-while loops. Understand how to use these loops to iterate over data, automate tasks, and solve common programming problems.",
                false,
                "https://www.youtube.com/watch?v=s9wW2PpJsmQ"
            ),
            Lessons(
                "5",
                "CSS",
                "30 min",
                "While not directly related to JavaScript, CSS is crucial for designing visually appealing websites. This lesson introduces the basics of CSS, including selectors, properties, and values. Learn how to style your web pages, enhance user interfaces, and create responsive designs that work across various devices",
                false,
                "https://www.youtube.com/watch?v=1PnVor36_40"
            )
        )
        val json = gson.toJson(defaultLessonsList)
        editor.putString("lessonsList", json)
        editor.apply()
        return defaultLessonsList
    }
}