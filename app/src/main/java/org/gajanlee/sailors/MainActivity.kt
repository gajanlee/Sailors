package org.gajanlee.sailors

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import org.gajanlee.sailors.buss.BaseActivity
import org.gajanlee.sailors.buss.Paper
import org.gajanlee.sailors.buss.PaperAdapter
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.FileCallback
import okhttp3.Request
import okhttp3.Response
import org.gajanlee.sailors.buss.NavBaseActivity
import java.io.File

class DownloadFileCallBacks(destDir: String, destName: String): FileCallback(destDir, destName) {

    override fun onResponse(isFromCache: Boolean, t: File?, request: Request?, response: Response?) {
        Log.d("d", "download done")
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class MainActivity : NavBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val paperList = ArrayList<Paper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)


        paperList.add(Paper(1, "haha.pdf", "/data", "axiv"))
        paperList.add(Paper(20, "haha.pdf", "/data", "axiv"))
        paperList.add(Paper(10, "haha.pdf", "/data", "axiv"))

        val adapter = PaperAdapter(this@MainActivity, R.layout.paper_item, paperList)
        val listView = findViewById(R.id.papers_list) as ListView
        listView.adapter = adapter
        listView.setOnItemClickListener {
            parent, view, position, id ->
                val paper = paperList.get(position)
                Toast.makeText(this@MainActivity, paper.name, Toast.LENGTH_LONG).show()
                if(position == 1) {
                    Log.d("path", Environment.getExternalStorageDirectory().path + "temp")
                    Thread {
                        OkHttpUtils.get("https://arxiv.org/pdf/1805.02220.pdf")
                                //.headers("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.6) Gecko/20070802 SeaMonkey/1.1.4")
                                .tag(this).execute(DownloadFileCallBacks(Environment.getExternalStorageDirectory().path + "/temp", "test.pdf"))

                    }.start()
                } else {
                    val intent = Intent(this@MainActivity, PreviewActivity::class.java)
                    intent.putExtra("pdf_url", "https://arxiv.org/pdf/1805.02220.pdf")
                    startActivity(intent)
                }


        }

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


}
