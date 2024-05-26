package woowacourse.shopping.study

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * [FirstAdapter]와 [FirstAdapter]의 공통된 부분을 추출한 [SharedViewHolder]를 상속받는 어댑터.
 *
 * 둘다 [SharedViewHolder]를 공유하고 있고, 두 어뎁터 모두 MultiViewType을 사용하고 있다.
 * */
class FirstAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<SharedViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 1) {
            SharedViewHolder.FIRST_VIEW_TYPE
        } else {
            SharedViewHolder.SECOND_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SharedViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        if (viewType == SharedViewHolder.FIRST_VIEW_TYPE) {
            return SharedViewHolder.FirstViewHolder(view)
        }
        return SharedViewHolder.SecondViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SharedViewHolder,
        position: Int,
    ) {
        holder.textView.text = items[position].content
        Log.e("StudyActivity", "KkosangAdapter - id: ${getItemId(position)}")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id
}

class SecondAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<SharedViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            SharedViewHolder.FIRST_VIEW_TYPE
        } else {
            SharedViewHolder.SECOND_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SharedViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        if (viewType == SharedViewHolder.FIRST_VIEW_TYPE) {
            return SharedViewHolder.FirstViewHolder(view)
        }
        return SharedViewHolder.SecondViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SharedViewHolder,
        position: Int,
    ) {
        holder.textView.text = items[position].content
        Log.e("StudyActivity", "KkosangAdapter - id: ${getItemId(position)}")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id
}

sealed class SharedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract val textView: TextView

    class FirstViewHolder(view: View) : SharedViewHolder(view) {
        override val textView: TextView = view.findViewById(android.R.id.text1)
    }

    class SecondViewHolder(view: View) : SharedViewHolder(view) {
        override val textView: TextView = view.findViewById(android.R.id.text1)
    }

    companion object {
        const val FIRST_VIEW_TYPE = 100
        const val SECOND_VIEW_TYPE = 200
    }
}
