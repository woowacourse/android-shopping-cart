package woowacourse.shopping.study

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 각각, [FirstAdapter2]와 [SecondAdapter2]의 [FirstViewHolder]와 [SecondViewHolder]를 상속받는 어댑터.
 *
 * 공유하는 뷰홀더가 없다.
 * 멀티 뷰타입 없다.
 * */
class FirstViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(android.R.id.text1)
}

class FirstAdapter2(private val items: List<Item>) :
    RecyclerView.Adapter<FirstViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FirstViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return FirstViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FirstViewHolder,
        position: Int,
    ) {
        holder.textView.text = items[position].content
        Log.e("StudyActivity", "KkosangAdapter - id: ${getItemId(position)}")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id
}

class SecondViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(android.R.id.text1)
}

class SecondAdapter2(private val items: List<Item>) :
    RecyclerView.Adapter<SecondViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SecondViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return SecondViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SecondViewHolder,
        position: Int,
    ) {
        holder.textView.text = items[position].content
        Log.e("StudyActivity", "KkosangAdapter - id: ${getItemId(position)}")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id
}
