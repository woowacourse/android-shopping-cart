package woowacourse.shopping.study

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 각각, [FirstAdapter3]와 [SecondAdapter3] 는 [ViewHolder]를 공유하고 있다.
 * 멀티 뷰타입 없다.
 * */
class FirstAdapter3(private val items: List<Item>) :
    RecyclerView.Adapter<ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.textView.text = items[position].content
        Log.e("StudyActivity", "OdooongAdapter3 - id: ${getItemId(position)}")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(android.R.id.text1)
}

class SecondAdapter3(private val items: List<Item>) :
    RecyclerView.Adapter<ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.textView.text = items[position].content
        Log.e("StudyActivity", "KkosangAdapter3 - id: ${getItemId(position)}")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id - 4
}
