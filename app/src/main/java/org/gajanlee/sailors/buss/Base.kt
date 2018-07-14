package org.gajanlee.sailors.buss

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

object ActivityCollector {
    private val activities = mutableListOf<AppCompatActivity>()

    fun addActivity(activity: AppCompatActivity) {
        activities.add(activity)
    }

    fun removeActivity(activity: AppCompatActivity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities)
            if (!activity.isFinishing()) activity.finish()
    }

}

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        Log.d("BaseActivity", javaClass.simpleName)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}