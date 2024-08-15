package com.example.project_g09.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g09.R
import com.example.project_g09.models.Lessons

class LessonsListAdapter(
    var yourLessonsList: List<Lessons>,
    var functionClickToDetails: (Int)->Unit)
    : RecyclerView.Adapter<LessonsListAdapter.LessonsListViewHolder>(){

        inner class LessonsListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsListViewHolder {
        val view:View = LayoutInflater.from(parent.context)
            .inflate(com.example.project_g09.R.layout.row_layout, parent,false)
        return LessonsListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return yourLessonsList.size
    }

    override fun onBindViewHolder(holder: LessonsListViewHolder, position: Int) {
        val currLesson:Lessons = yourLessonsList.get(position)

        val tvLabel = holder.itemView.findViewById<TextView>(com.example.project_g09.R.id.tvRow1)
        tvLabel.text = currLesson.name

        val tv2Label = holder.itemView.findViewById<TextView>(com.example.project_g09.R.id.tvRow2)
        tv2Label.text = "Length: ${currLesson.length}"

        val tv3Label = holder.itemView.findViewById<ImageView>(com.example.project_g09.R.id.tvIsComplete)
        if (currLesson.isComplete == true) {
            tv3Label.setImageResource(com.example.project_g09.R.drawable.baseline_done_24)
        } else {
            tv3Label.setImageDrawable(null)
        }

        val button = holder.itemView.findViewById<LinearLayout>(R.id.btn_to_details)
        button.setOnClickListener {
            functionClickToDetails(position)
        }

        val context = holder.itemView.context
        val imageId = context.resources.getIdentifier("id_${currLesson.id}", "drawable", context.packageName)
        val iv = holder.itemView.findViewById<ImageView>(R.id.imageView)
        iv.setImageResource(imageId)
    }
}