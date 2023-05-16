package woowacourse.shopping.feature.main.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel

class MainProductAdapter(
    items: List<ProductUiModel>,
    private val listener: ProductClickListener
) : RecyclerView.Adapter<MainProductViewHolder>() {
    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        return MainProductViewHolder.create(parent)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        holder.bind(_items[position], listener)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun addItems(newItems: List<ProductUiModel>) {
        val preSize = _items.size
        _items.addAll(newItems.toList())
        notifyItemRangeInserted(preSize, newItems.size)
    }

    companion object {
        const val VIEW_TYPE = 222
    }
}
