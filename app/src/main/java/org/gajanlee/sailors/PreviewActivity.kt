package org.gajanlee.sailors

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast

import java.io.IOException
import java.io.InputStream

import android.provider.ContactsContract.CommonDataKinds.Website.URL
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        val pdfView = findViewById(R.id.pdfViewer) as PDFView
        val allpdfName = Environment.getExternalStorageDirectory().path + "/temp"
        val file = File(allpdfName, "test.pdf")
        pdfView.fromFile(file).defaultPage(1).load()
        Thread {

            Log.d("Download", "start")
            lateinit var _is: InputStream
            try {
                val url = java.net.URL("https://arxiv.org/pdf/1805.02220.pdf")
                _is = url.openStream()

                _is.close()
            } catch (e: IOException) {
                Log.e("Download", "Error")
            }
            getExternalFilesDir(null)
            Log.d("Download", "start2")
            pdfView.fromStream(_is)  // fromUri(Uri.parse("https://arxiv.org/pdf/1805.02220.pdf"))
                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .load();

            Log.d("Download", "end3")

        }
        // Log.d("path", resources.getIdentifier("test.pdf", "tests", applicationInfo.packageName))

    }

}
