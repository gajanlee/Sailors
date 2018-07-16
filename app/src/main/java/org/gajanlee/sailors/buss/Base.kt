package org.gajanlee.sailors.buss

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import org.gajanlee.sailors.DownloadActivity
import org.gajanlee.sailors.R

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
/*  A basic activity to replace inline AppCompatActivity,
    it contains activity manager, log and other extension functions.
*/
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


/* A NavBarBasic Activity
* the layout.xml needs a popup button.*/
open class NavBaseActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_download) {
            // Handle the camera action
            DownloadActivity.actionStart(this@NavBaseActivity)
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}