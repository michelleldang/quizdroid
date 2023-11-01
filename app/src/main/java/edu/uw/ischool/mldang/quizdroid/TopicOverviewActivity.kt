package edu.uw.ischool.mldang.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TopicOverviewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val topic = intent.getStringExtra("topic")
        var description = findViewById<TextView>(R.id.topicDescription)
        val topicString = when (topic) {
            "Math" -> R.string.math
            "Physics" -> R.string.physics
            else -> R.string.marvel
        }
        description.text = getString(topicString)

        val beginButton= findViewById<Button>(R.id.begin)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java).putExtra("topic", topic)
            startActivity(intent)
        }
    }
}