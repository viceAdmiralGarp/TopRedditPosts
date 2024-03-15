package com.example.topredditposts.img

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

class ImageLoader {
    companion object {
        fun downloadImage(url: String, context: Context) {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
            downloadManager.enqueue(request)
        }
    }
}