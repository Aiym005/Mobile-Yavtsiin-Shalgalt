package com.example.mobileyavts.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params){
    override fun doWork(): Result {
        makeStatusNotification(inputData.getString("Title").toString() , applicationContext)
        return Result.success()
    }
}