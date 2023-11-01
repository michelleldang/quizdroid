package edu.uw.ischool.mldang.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionActivity: AppCompatActivity()  {

    private var currentQ: Int = SetStat.totalAnswered + 1
    private var questionList: ArrayList<Question>? = null
    private var questionText: TextView? = null
    private var radioGroup: RadioGroup? = null
    private var radioButton1: RadioButton? = null
    private var radioButton2: RadioButton? = null
    private var radioButton3: RadioButton? = null
    private var radioButton4: RadioButton? = null
    private var answer: Int = 0
    private var submit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        setViews()
        SetStat.topic = intent.getStringExtra("topic").toString()

        when(SetStat.topic) {
            "Math" -> getMathQuestions()
            "Physics" -> getPhysicsQuestions()
            "Marvel Super Heroes" -> getMarvelQuestions()
        }
        setQuestions()
        submitQuestion()
    }
    private fun setViews() {
        questionText= findViewById(R.id.question)
        radioGroup = findViewById(R.id.radioGroup)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        radioButton4 = findViewById(R.id.radioButton4)
        submit = findViewById(R.id.submit)

        submit?.visibility = Button.INVISIBLE

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            val selected = checkedId != -1
            submit?.visibility = if (selected) Button.VISIBLE else Button.INVISIBLE
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

    private fun setQuestions() {
        questionList?.let{
            val question: Question = questionList!![currentQ-1]
            println("q : $question, ql: $questionList")
            questionText?.text = question.question
            radioButton1?.text = question.one
            radioButton2?.text = question.two
            radioButton3?.text = question.three
            radioButton4?.text = question.four
            answer = question.correctAnswer
        }
        when(currentQ) {
            1 -> SetStat.totalQuestions = questionList?.size
        }
    }

    private fun submitQuestion() {
        submit?.setOnClickListener {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedOption)
            var answerId = when(answer) {
                1 -> R.id.radioButton1
                2 -> R.id.radioButton2
                3 -> R.id.radioButton3
                else -> R.id.radioButton4
            }
            val correctA = findViewById<RadioButton>(answerId)
                val intent = Intent(this, AnswerActivity::class.java)
                .putExtra("answer", radioButton.text)
                .putExtra("correctAnswer", correctA?.text)
            startActivity(intent)
        }
    }
}