package edu.uw.ischool.mldang.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        supportActionBar?.title = "Preferences"
        findViewById<Button>(R.id.button).setOnClickListener {
            if (isNetworkAvailable()) {
                scheduleDownloadService(this)
            } else {
                handleNoInternetAccess()
            }
        }
        if (findViewById<View?>(R.id.idFrameLayout) != null) {
            if (savedInstanceState != null) {
                return
            }
            fragmentManager.beginTransaction().add(R.id.idFrameLayout, SettingsFragment()).commit()
        }

    }
    private var receiver : BroadcastReceiver? = null
    private fun scheduleDownloadService(contexts: Context) {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showToast("WAKE UP! ${intent?.action}")
                download(contexts)
            }
        }
        val filter = IntentFilter(ALARM_ACTION)
        registerReceiver(receiver, filter)
        val intent = Intent(ALARM_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val min = Integer.parseInt(pref.getString("minutes", "1"))
        val intervalMillis =    min.toLong()*10 * 1000 //min.toLong()
        Log.d("minutes", "$min")
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + intervalMillis,
            pendingIntent
        )
    }
    fun download(context: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val url = pref.getString("url", "https://tednewardsandbox.site44.com/questions.json")
        Log.d("url link", "$url")
        if (isNetworkAvailable()) {
            if (url != null) {
                val downloader = AndroidDownloader(context)
                Log.d("file path", "${getExternalFilesDir(null)}")
                downloader.downloadFile(url)
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        } else {
            handleNoInternetAccess()
        }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    private fun handleNoInternetAccess() {
        showToast("No internet access. Please check your connection.")
            unregisterReceiver(receiver)

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}