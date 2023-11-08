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

        val topic = intent.getSerializableExtra("topic") as? Topic
        var description = findViewById<TextView>(R.id.topicDescription)

        if (topic != null) {
            description.text = topic.shortDescription + "\n\n${topic.longDescription}"
        }

        val beginButton= findViewById<Button>(R.id.begin)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java).putExtra("topic", topic)
            startActivity(intent)
        }
    }
}