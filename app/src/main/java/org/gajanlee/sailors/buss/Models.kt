package org.gajanlee.sailors.buss

import android.content.Context
import android.support.v7.widget.RecyclerView
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
