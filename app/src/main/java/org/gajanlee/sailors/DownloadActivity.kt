package org.gajanlee.sailors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.*
import org.gajanlee.sailors.buss.DownloadCollector
import org.gajanlee.sailors.buss.DownloadItem
import org.gajanlee.sailors.buss.DownloadItemAdapter
import org.gajanlee.sailors.buss.NavBaseActivity
import java.util.ArrayList


class DownloadActivity : NavBaseActivity() {

    companion object {
        fun actionStart(context: Context, data1: String = "123") {
            val intent = Intent(context, DownloadActivity::class.java)
            intent.putExtra("params", data1)
            context.startActivity(intent)
        }
    }

    private var download_list_layout: LinearLayout? = null
    private var download_config_layout: LinearLayout? = null

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                download_config_layout!!.visibility = View.VISIBLE
                download_list_layout!!.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                download_config_layout!!.visibility = View.GONE
                download_list_layout!!.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        download_config_layout = findViewById(R.id.download_config) as LinearLayout
        download_list_layout = findViewById(R.id.download_list_layout) as LinearLayout

        (findViewById(R.id.btn_download) as Button).setOnClickListener {view ->
            Toast.makeText(this@DownloadActivity, "Start Download!", Toast.LENGTH_SHORT).show()
            val listView = findViewById(R.id.download_listview) as ListView
            DownloadCollector.listView = listView
            val items = ArrayList<DownloadItem>()
            val item = DownloadItem("https://arxiv.org/pdf/1805.02220.pdf", "")
            items.add(item)
            val adapter = DownloadItemAdapter(this@DownloadActivity, R.layout.download_item, items)
            DownloadCollector.adapter = adapter
            listView.adapter = adapter
            DownloadCollector.addItem(item)

        }
    }

}
