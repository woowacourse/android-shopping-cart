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

    fun updateRecentProductItems(newRecentProducts: ProductListItem) {
        items[RECENT_PRODUCT_POSITION] = newRecentProducts
        notifyItemChanged(RECENT_PRODUCT_POSITION)
    }

    fun updateProductItems(newProductItems: List<ProductListItem.ShoppingProductItem>) {
        val oldProductItems = items.filterIsInstance<ProductListItem.ShoppingProductItem>()
        if (oldProductItems.size == newProductItems.size) {
            updateSingleItem(
                newProductItems,
                oldProductItems,
            )
        } else {
            addItem(
                newProductItems,
                getIntersectCount(oldProductItems, newProductItems),
            )
        }
    }

    private fun getIntersectCount(
        oldProductItems: List<ProductListItem.ShoppingProductItem>,
        newProductItems: List<ProductListItem.ShoppingProductItem>,
    ): Int {
        val a =
            oldProductItems.filter {
                newProductItems.contains(it)
            }
        return a.size
    }

    private fun updateSingleItem(
        newProductItems: List<ProductListItem.ShoppingProductItem>,
        oldProductItems: List<ProductListItem.ShoppingProductItem>,
    ) {
        val changedIndex =
            oldProductItems.indexOfFirst {
                !newProductItems.contains(it)
            }
        updateItems(newProductItems)
        notifyItemChanged(changedIndex + 1)
    }

    private fun addItem(
        newProducts: List<ProductListItem.ShoppingProductItem>,
        intersectCount: Int,
    ) {
        val startPosition = items.size + 1
        updateItems(newProducts)
        notifyItemRangeInserted(startPosition, newProducts.size - intersectCount)
    }

    private fun updateItems(newProducts: List<ProductListItem>) {
        val recentItems = items[RECENT_PRODUCT_POSITION]
        items.apply {
            clear()
            add(recentItems)
            addAll(newProducts)
        }
    }

    companion object {
        private const val RECENT_PRODUCT_POSITION = 0
        private const val LOAD_MORE_COUNT = 1
    }
}
