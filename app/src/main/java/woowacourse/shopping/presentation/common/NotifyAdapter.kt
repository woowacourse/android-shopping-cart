package woowacourse.shopping.presentation.common

import androidx.recyclerview.widget.RecyclerView

abstract class NotifyAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items: MutableList<T> = mutableListOf()
    fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
