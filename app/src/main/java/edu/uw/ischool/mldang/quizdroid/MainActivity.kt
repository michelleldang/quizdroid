package edu.uw.ischool.mldang.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val values = listOf(
            "Math",
            "Physics",
            "Marvel Super Heroes")
        val adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, values)
        var listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val select = values[position]
            val intent = Intent(this, TopicOverviewActivity::class.java).putExtra("topic", select)
            startActivity(intent)
        }
    }
}