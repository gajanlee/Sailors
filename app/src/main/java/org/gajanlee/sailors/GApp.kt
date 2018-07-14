package org.gajanlee.sailors

import android.app.Application
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.model.HttpHeaders

class GApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val headers = HttpHeaders()
        headers.put("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.6) Gecko/20070802 SeaMonkey/1.1.4")
        OkHttpUtils.init(this)


    }
}
