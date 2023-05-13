package woowacourse.shopping.feature.main.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainProductAdapter(
    items: List<MainProductItemModel>
) : RecyclerView.Adapter<MainProductViewHolder>() {
    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        return MainProductViewHolder.create(parent)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun addItems(newItems: List<MainProductItemModel>) {
        val preSize = _items.size
        _items.addAll(newItems.toList())
        notifyItemRangeInserted(preSize, newItems.size)
    }

    companion object {
        const val VIEW_TYPE = 222
    }
}
