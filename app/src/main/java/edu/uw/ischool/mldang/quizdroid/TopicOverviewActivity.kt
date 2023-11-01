package edu.uw.ischool.mldang.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TopicOverviewActivity: AppCompatActivity() {
    private var questionList: ArrayList<Question>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val topic = intent.getStringExtra("topic")
        var description = findViewById<TextView>(R.id.topicDescription)
        val topicString = when (topic) {
            "Math" -> {
                getMathQuestions()
                R.string.math}
            "Physics" -> {
                getPhysicsQuestions()
                R.string.physics}
            else -> {
                getMarvelQuestions()
                R.string.marvel}
        }
        description.text = getString(topicString) + "\n\nThere are ${questionList?.size} questions in this topic"

        val beginButton= findViewById<Button>(R.id.begin)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java).putExtra("topic", topic)
            startActivity(intent)
        }
    }
    private fun getMathQuestions() {
        questionList = Questions.getMathQuestions()
    }
    private fun getPhysicsQuestions() {
        questionList = Questions.getPhysicsQuestions()
    }
    private fun getMarvelQuestions() {
        questionList = Questions.getMarvelQuestions()
    }
}