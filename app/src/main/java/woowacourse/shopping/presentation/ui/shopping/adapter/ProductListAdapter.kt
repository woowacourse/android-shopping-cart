package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderRecentProductsBinding
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

class ProductListAdapter(
    private val shoppingHandler: ShoppingHandler,
    private val items: MutableList<ProductListItem> =
        mutableListOf(
            ProductListItem.RecentProductItems(emptyList()),
        ),
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            RECENT_PRODUCT_POSITION -> ShoppingViewType.RecentProduct.value
            itemCount - LOAD_MORE_COUNT -> ShoppingViewType.LoadMore.value
            else -> ShoppingViewType.Product.value
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ShoppingViewType.of(viewType)) {
            ShoppingViewType.RecentProduct -> {
                val binding = HolderRecentProductsBinding.inflate(inflater, parent, false)
                ProductViewHolder.RecentlyViewedProductViewHolder(binding, shoppingHandler)
            }

            ShoppingViewType.Product -> {
                val binding = ItemShoppingProductBinding.inflate(inflater, parent, false)
                ProductViewHolder.ShoppingProductViewHolder(binding, shoppingHandler)
            }

            ShoppingViewType.LoadMore -> {
                val binding = ItemLoadBinding.inflate(inflater, parent, false)
                ProductViewHolder.LoadViewHolder(binding, shoppingHandler)
            }
        }
    }

    override fun getItemCount(): Int = items.size + LOAD_MORE_COUNT

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder.RecentlyViewedProductViewHolder -> holder.bind(items[position] as ProductListItem.RecentProductItems)
            is ProductViewHolder.ShoppingProductViewHolder -> holder.bind(items[position] as ProductListItem.ShoppingProductItem)
            is ProductViewHolder.LoadViewHolder -> holder.bind()
        }
    }

    fun insertRecentProductItems(newRecentProducts: ProductListItem) {
        items[RECENT_PRODUCT_POSITION] = newRecentProducts
        notifyItemRangeInserted(RECENT_PRODUCT_POSITION, 1)
    }

    fun insertProductItemsAtPosition(
        startPosition: Int,
        newProducts: List<ProductListItem>,
    ) {
        items.apply {
            val recentItems = items[RECENT_PRODUCT_POSITION]
            clear()
            add(recentItems)
            addAll(newProducts)
        }
        notifyItemRangeInserted(startPosition, newProducts.size)
    }

    companion object {
        private const val RECENT_PRODUCT_POSITION = 0
        private const val LOAD_MORE_COUNT = 1
    }
}
