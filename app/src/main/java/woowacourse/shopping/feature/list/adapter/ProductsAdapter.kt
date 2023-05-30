package woowacourse.shopping.feature.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.list.item.ProductView.RecentProductsItem
import woowacourse.shopping.feature.list.viewholder.ItemViewHolder
import woowacourse.shopping.feature.list.viewholder.ProductViewHolder
import woowacourse.shopping.feature.list.viewholder.RecentProductsViewHolder

class ProductsAdapter(
    private var items: List<ProductView> = listOf(),
    private val onItemClick: (ProductView) -> Unit,
    private val onAddClick: (ProductView) -> Unit,
    private val onPlusClick: (ProductView) -> Unit,
    private val onMinusClick: (ProductView) -> Unit,
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CartProductItem -> ProductView.TYPE_CART_PRODUCT
            is RecentProductsItem -> ProductView.TYPE_RECENT_PRODUCTS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductView.TYPE_CART_PRODUCT -> {
                ProductViewHolder(
                    parent = parent,
                    onItemClick = { position -> onItemClick(items[position]) },
                    onAddClick = { position -> onAddClick(items[position]) },
                    onPlusClick = { position -> onPlusClick(items[position]) },
                    onMinusClick = { position -> onMinusClick(items[position]) },
                )
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
                holder.bind(items[position] as CartProductItem)
            }
            is RecentProductsViewHolder -> {
                holder.bind(items[position] as RecentProductsItem)
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

    fun setItem(item: CartProductItem) {
        val position = items.indexOf(item)
        (items[position] as CartProductItem).updateCount(item.count)
        notifyItemChanged(position)
    }
}
