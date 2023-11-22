package edu.uw.ischool.mldang.quizdroid


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


const val ALARM_ACTION = "edu.uw.ischool.mldang.ALARM"
class MainActivity : AppCompatActivity() {
    private var items: List<Topic> = mutableListOf()
    private lateinit var preferenceBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!File("/storage/emulated/0/Android/data/edu.uw.ischool.mldang.quizdroid/files/Download/","questions.json").exists()) {
            val downloader = AndroidDownloader(this)
            downloader.downloadFile("https://tednewardsandbox.site44.com/questions.json")
        }
        preferenceBtn = findViewById(R.id.pref_btn)
        preferenceBtn.setOnClickListener {
            val i = Intent(this@MainActivity, PreferencesActivity::class.java)
            startActivity(i)
        }
        val f = IntentFilter("android.intent.action.DOWNLOAD_COMPLETE")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val quizApp = (application as QuizApp)
                val repository = quizApp.topicRepository
                items = repository.getAll()
                val adapter =
                    context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, items.map{it.title}) }
                val listView = findViewById<ListView>(R.id.list_view)
                listView.adapter = adapter
            }
        }, f)
        preferenceBtn = findViewById(R.id.pref_btn)
        preferenceBtn.setOnClickListener {
            val i = Intent(this@MainActivity, PreferencesActivity::class.java)
            startActivity(i)
        }
        val quizApp = (application as QuizApp)
        val repository = quizApp.topicRepository
        items = repository.getAll()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items.map{it.title})
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val select = items[position]
            val intent = Intent(this, TopicOverviewActivity::class.java).putExtra("topic", select)
            startActivity(intent)
        }

    }
}