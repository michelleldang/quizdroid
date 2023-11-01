package edu.uw.ischool.mldang.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnswerActivity: AppCompatActivity() {
    private var answer: String? = null
    private var correctA: String? = null
    private var next: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        setAnswers()
        nextPage()
    }

    private fun setAnswers() {
        answer = intent.getStringExtra("answer")
        correctA = intent.getStringExtra("correctAnswer")
        if (answer == correctA) {
            SetStat.correct++
        }
        SetStat.totalAnswered++
        val totalText = findViewById<TextView>(R.id.totalAnswer)
        totalText.text = "\nYour Answer: $answer\nCorrect Answer: $correctA\n\n" +
                "You have ${SetStat.correct} out of ${SetStat.totalAnswered} correct."

        next = findViewById(R.id.next)
    }
    private fun nextPage() {
        if (SetStat.totalAnswered == SetStat.totalQuestions) {
            next?.text = "Finish"
            next?.setOnClickListener {
                SetStat.totalAnswered = 0
                SetStat.totalQuestions = null
                SetStat.correct = 0
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            next?.text = "Next"
            next?.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java).putExtra("topic", SetStat.topic)
                startActivity(intent)
            }
        }
    }

}

