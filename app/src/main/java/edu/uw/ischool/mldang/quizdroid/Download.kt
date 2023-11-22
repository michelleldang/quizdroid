package edu.uw.ischool.mldang.quizdroid

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import java.io.File


interface Downloader {
    fun downloadFile(url: String): Long
}

class AndroidDownloader(
    private val context: Context
): Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        Log.d("context", "$context")

        val request = DownloadManager.Request(url.toUri())
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Downloading Questions")
            .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "questions.json")
        return downloadManager.enqueue(request)
    }
}
class DownloadCompletedReceiver : BroadcastReceiver() {
    @SuppressLint("Range")

    override fun onReceive(context: Context?, intent: Intent?) {
//        sendBroadcast(Intent("com.example.action.DOWNLOAD_COMPLETE")
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (downloadId != -1L) {
                val query = DownloadManager.Query().setFilterById(downloadId)
                val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val cursor = downloadManager.query(query)

                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        if (isFileExists()) {
                            deleteFile()
                            println("Download with ID $downloadId finished!")
                            sendDownloadCompleteBroadcast(context)
                        }

                    }else {
                        Toast.makeText(context,"Failed to download questions. Retry or quit the application.", Toast.LENGTH_SHORT).show()
                    }
                }

                cursor.close()
            }
        }
    }

    private fun isFileExists(): Boolean {
            val file = File("/storage/emulated/0/Android/data/edu.uw.ischool.mldang.quizdroid/files/Download", "questions.json")
            return file.exists()
    }

    private fun deleteFile() {
            val file = File("/storage/emulated/0/Android/data/edu.uw.ischool.mldang.quizdroid/files/Download" )
            file.delete()
    }
    private fun sendDownloadCompleteBroadcast(context: Context?) {
        val intent = Intent("android.intent.action.DOWNLOAD_COMPLETE")
        context?.sendBroadcast(intent)
    }
//    fun deleteRecursive(fileOrDirectory: File) {
//        if (fileOrDirectory.isDirectory) for (child in fileOrDirectory.listFiles()) deleteRecursive(
//            child
//        )
//        fileOrDirectory.delete()
//    }
}
