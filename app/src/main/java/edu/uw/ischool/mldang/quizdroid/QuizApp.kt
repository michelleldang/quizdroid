package edu.uw.ischool.mldang.quizdroid

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*



class QuizApp: Application() {
    lateinit var topicRepository : TopicRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "QuizApp onCreate() is being loaded and run");
        topicRepository = FileTopicRepository(this)

    }
}

interface TopicRepository {
    fun getAll() : List<Topic>
}

class FileTopicRepository(val context: Context): TopicRepository {
    val TAG = "FileTopicRepository"
    lateinit var topics : MutableList<Topic>
    private var downloading: Boolean = false
    init {
        readItems()
    }
    override fun getAll(): List<Topic> {
        return topics
    }

    private fun readItems() {
        Log.v(TAG, "Calling readItems()")
        topics = mutableListOf()

        try {
            if (!downloading) {
                downloading = true
            val reader = BufferedReader(FileReader(File(context.filesDir, "data/questions.json")))
            val json = reader.readText()

            topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
                downloading = false
            } else {
                Log.d(TAG, "Downloading")
            }

        } catch (e: IOException) {
            downloading = false
            Log.e(TAG, "Error while reading items", e)
        }
    }
}

data class Quiz(
    val text: String,
    val answer: Int,
    val answers: ArrayList<String>

): Serializable

data class Topic(
    val title: String,
    val desc: String,
    val questions: ArrayList<Quiz>
): Serializable