package org.gajanlee.sailors.buss

import android.content.Context
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.text.format.Formatter
import android.util.FloatMath
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.FileCallback
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import org.gajanlee.sailors.R
import java.io.File
import java.lang.Exception
import java.util.ArrayList

data class Paper(val id: Int, val name: String, val path: String, val source: String)

class PaperAdapter(context: Context, resourceId: Int, data: List<Paper>): ArrayAdapter<Paper>(context, resourceId, data) {
    private val resourceId = resourceId

    // convertView maybe null, so we should nominate it as a nullable variable
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val paper = getItem(pos)
        lateinit var view: View
        lateinit var viewHoler: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null)
            viewHoler = ViewHolder(view.findViewById(R.id.paper_name) as TextView)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHolder
        }
        viewHoler.paperName.text = paper.name
        return view
    }

    inner class ViewHolder(val paperName: TextView)
}

data class DownloadItem(val url: String, var name: String, val category: String="") {
    var currentSize: Long = 0
    var totalSize: Long = 0
    var progress: Float = 0f
    var networkSpeed: Long = 0
    var done = false
}

class DownloadItemAdapter(context: Context, resourceId: Int, data: List<DownloadItem>): ArrayAdapter<DownloadItem>(context, resourceId, data) {
    private val resourceId = resourceId

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)

        lateinit var view: View
        lateinit var viewHoler: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null)
            viewHoler = ViewHolder(view.findViewById(R.id.download_url) as TextView, view.findViewById(R.id.download_current) as TextView)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHolder
        }
        viewHoler.downloadUrl.text = item.url
        viewHoler.downloadCurrent.text = "0KB"
        return view
    }

    fun update(index: Int, listView: ListView) {
        //listView.firstVisiblePosition
        Log.d("firstPos", listView.firstVisiblePosition.toString() + "/" + index.toString() + "/" + listView.lastVisiblePosition)
        val view = listView.getChildAt(index - listView.firstVisiblePosition)
        val viewholder = view.tag as ViewHolder
        viewholder.downloadCurrent = view.findViewById(R.id.download_current) as TextView
        viewholder.downloadCurrent.text = "10KB"
    }

    inner class ViewHolder(val downloadUrl: TextView, var downloadCurrent: TextView)

}

object DownloadCollector {
    var adapter: DownloadItemAdapter? = null
    var listView: ListView? = null
    private val download_items = ArrayList<DownloadItem>()

    fun addItem(item: DownloadItem) {
        if (item.name == "") item.name = item.url.substringAfterLast("/")
        OkHttpUtils.get(item.url).tag(item).execute(DownloadFileCallBack(Environment.getExternalStorageDirectory().path + "/temp", item.name, item))
        download_items.add(item)
    }

    fun removeItem(item: DownloadItem) {
        download_items.remove(item)
    }

    fun update(item: DownloadItem) {
        listView!!.let { adapter?.update(download_items.indexOf(item), listView as ListView) }
    }
}

class DownloadFileCallBack(val destDir: String, val destName: String, val item: DownloadItem): FileCallback(destDir, destName) {
    private val _item = item
    override fun onResponse(isFromCache: Boolean, t: File?, request: Request?, response: Response?) {
        item.done = true
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun downloadProgress(currentSize: Long, totalSize: Long, progress: Float, networkSpeed: Long) {
        item.currentSize = currentSize
        item.totalSize = totalSize
        item.progress = progress
        item.networkSpeed = networkSpeed
        Log.d("download", item.totalSize.toString())
        DownloadCollector.update(item)
    }

    override fun onError(isFromCache: Boolean, call: Call?, response: Response?, e: Exception?) {
        super.onError(isFromCache, call, response, e)
        DownloadCollector.removeItem(item)
    }
}
