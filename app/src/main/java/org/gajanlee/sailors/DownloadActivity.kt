package org.gajanlee.sailors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import org.gajanlee.sailors.buss.NavBaseActivity


class DownloadActivity : NavBaseActivity() {

    companion object {
        fun actionStart(context: Context, data1: String = "123") {
            val intent = Intent(context, DownloadActivity::class.java)
            intent.putExtra("params", data1)
            context.startActivity(intent)
        }
    }

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        mTextMessage = findViewById(R.id.message) as TextView
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
