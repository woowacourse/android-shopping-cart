package woowacourse.shopping.view.home.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreButtonBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.cart.QuantityClickListener
import woowacourse.shopping.view.home.HomeClickListener
import woowacourse.shopping.view.home.adapter.product.ShoppingItem.Companion.PRODUCT_VIEW_TYPE
import woowacourse.shopping.view.home.adapter.product.ShoppingItem.LoadMoreItem
import woowacourse.shopping.view.home.adapter.product.ShoppingItem.ProductItem

class ProductAdapter(
    private val homeClickListener: HomeClickListener,
    val quantityClickListener: QuantityClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val shoppingItems: MutableList<ShoppingItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return shoppingItems[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == PRODUCT_VIEW_TYPE) {
            ProductViewHolder(ItemProductBinding.inflate(inflater, parent, false))
        } else {
            LoadMoreButtonViewHolder(ItemLoadMoreButtonBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val shoppingItem = shoppingItems[position]
        if (holder is ProductViewHolder && shoppingItem is ProductItem) {
            holder.bind(shoppingItem, homeClickListener, quantityClickListener)
        }
        if (holder is LoadMoreButtonViewHolder) {
            holder.bind(homeClickListener)
        }
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }

    fun loadData(
        productItems: List<ProductItem>,
        canLoadMore: Boolean,
    ) {
        val currentSize = if (shoppingItems.isEmpty()) 0 else shoppingItems.size - 1
        val items = productItems.subList(currentSize, productItems.size)

        shoppingItems.removeLastOrNull()
        shoppingItems += items

        if (canLoadMore) {
            shoppingItems += LoadMoreItem()
            notifyItemRangeInserted(shoppingItems.size, items.size + 1)
        } else {
            notifyItemRangeInserted(shoppingItems.size, items.size)
        }
    }

    fun updateData(updatedItems: List<ProductItem>) {
        updatedItems.forEach { updatedItem ->
            updateProductQuantity(updatedItem)
        }
    }

    fun updateProductQuantity(updatedProductItem: ProductItem) {
        val position =
            shoppingItems.indexOfFirst { item ->
                (item as ProductItem).product.id == updatedProductItem.product.id
            }

        if (position != -1) {
            shoppingItems[position] = updatedProductItem
            notifyItemChanged(position)
        }
    }
}
