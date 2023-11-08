package edu.uw.ischool.mldang.quizdroid

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.Serializable

class QuizApp: Application() {
    lateinit var topicRepository : TopicRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "QuizApp onCreate() is being loaded and run");
        topicRepository = FileTopicRepository()//this

    }
}

interface TopicRepository {
    fun getAll() : List<Topic>
}

class FileTopicRepository(/**val context: Context**/): TopicRepository {
//    val TAG = "FileTopicRepository"
//    lateinit var topic : MutableList<Topic>
//
//    init {
//        readItems()
//    }

    val topics : MutableList<Topic> = mutableListOf(
        Topic("Math", "Math is numbers, numbers are fun.", "There are 2 questions in this topic", arrayListOf(
            Quiz("What is 1+1?",
            "1", "2",
            "3", "4", 2),
            Quiz("What is 2+2",
        "1", "2",
        "3", "4", 4)
        )
        ),
        Topic("Physics", "This topic is physics. Physics is about the science of matter, motion, and energy.", "There are 2 questions in this topic", arrayListOf(
            Quiz("What is the formula for calculating kinetic energy?",
                "KE = 1/2(mv)^2", "KE = mgh",
                "KE = (mv)/t", "KE = 1/2 * mgt", 1),
            Quiz("What is the value of gravity?",
            "10 m/s^2", "5 m/s^2",
            "10.2 m/s^2", "9.8 m/s^2", 4)
        )
        ),
        Topic("Marvel Super Heroes", "This topic is Marvel. Marvel is a cinematic universe under Disney that hosts a variety of superheroes and villains", "There are 2 questions in this topic", arrayListOf(
            Quiz("How many stones were in the infinite gauntlet",
                "5", "6",
                "7", "8", 2
            ),
            Quiz("What is the name of the villain in End Game?",
            "Loki", "Ultron",
            "Green Goblin", "Thanos", 4)
        )
        )
        )

    override fun getAll(): List<Topic> {
        return topics
    }

//    private fun readItems() {
//        Log.v(TAG, "Calling readItems()")
//
//    }

}


data class Quiz(
    val question: String,
    val answerOne: String,
    val answerTwo: String,
    val answerThree: String,
    val answerFour: String,
    val correctAnswer: Int
): Serializable

data class Topic(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val questions: ArrayList<Quiz>
): Serializable
//{
//    override fun toString(): String {
//        return title
//    }
//}
