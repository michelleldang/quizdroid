package edu.uw.ischool.mldang.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    var items: List<Topic> = mutableListOf()
    lateinit var preferenceBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceBtn = findViewById(R.id.pref_btn)
        preferenceBtn.setOnClickListener {
            val i = Intent(this@MainActivity, PreferencesActivity::class.java)
            startActivity(i)
        }
        val quizApp = (application as QuizApp)
        val repository = quizApp.topicRepository
        items = repository.getAll()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items.map{it.title})
        var listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val select = items[position]
            val intent = Intent(this, TopicOverviewActivity::class.java).putExtra("topic", select)
            startActivity(intent)
        }
    }
}