package woowacourse.shopping.feature.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.viewholder.ItemViewHolder
import woowacourse.shopping.feature.list.viewholder.ProductViewHolder
import woowacourse.shopping.feature.list.viewholder.RecentProductsViewHolder

class ProductsAdapter(
    private var items: List<ProductView> = listOf(),
    private val onItemClick: (ProductView) -> Unit,
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ProductView.ProductItem -> ProductView.TYPE_PRODUCT
            is ProductView.RecentProductsItem -> ProductView.TYPE_RECENT_PRODUCTS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductView.TYPE_PRODUCT -> {
                ProductViewHolder(parent)
            }
            ProductView.TYPE_RECENT_PRODUCTS -> {
                RecentProductsViewHolder(parent)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(items[position] as ProductView.ProductItem, onItemClick)
            }
            is RecentProductsViewHolder -> {
                holder.bind(items[position] as ProductView.RecentProductsItem, onItemClick)
            }
        }
    }

    fun addItems(newItems: List<ProductView>) {
        val items = this.items.toMutableList()
        newItems.forEach {
            items.add(it)
        }
        this.items = items.toList()
        notifyItemRangeChanged(items.size, newItems.size)
    }

    fun setItems(items: List<ProductView>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
