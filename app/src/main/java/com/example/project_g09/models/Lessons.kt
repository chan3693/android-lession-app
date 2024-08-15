package com.example.project_g09.models

import java.io.Serializable

class Lessons:Serializable {
    var id:String
    var name:String
    var length:String
    var description:String
    var isComplete:Boolean
    var URL:String

    constructor(id: String, name: String, length: String, description:String,isComplete: Boolean, URL:String) {
        this.id = id
        this.name = name
        this.length = length
        this.description = description
        this.isComplete = isComplete
        this.URL = URL
    }

    override fun toString(): String {
        return "Lessons(No.: ${id}, name='$name', length='$length', isComplete='$isComplete', '$URL')"
    }

}