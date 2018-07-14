package org.gajanlee.sailors.buss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.gajanlee.sailors.R

data class Paper(val id: Int, val name: String, val path: String, val source: String)

class PaperAdapter(context: Context, resourceId: Int, data: List<Paper>): ArrayAdapter<Paper>(context, resourceId, data) {
    private val resourceId = resourceId

    // convertView maybe null, so we should nominate it as a nullable variable
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val paper = getItem(pos)
        val view = LayoutInflater.from(context).inflate(resourceId, null)
        val paperName = view.findViewById(R.id.paper_name) as TextView
        paperName.text = paper.name
        return view
    }
}
