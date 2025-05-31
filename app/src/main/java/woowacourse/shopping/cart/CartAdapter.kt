package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.cart.event.CartEventHandler
import woowacourse.shopping.cart.viewHolder.CartViewHolder
import woowacourse.shopping.cart.viewHolder.PaginationButtonViewHolder
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.util.CartAdapterDiffCallback

class CartAdapter(
    private val cartHandler: CartEventHandler,
    private val handler: ProductQuantityHandler,
) : ListAdapter<CartAdapterItem, RecyclerView.ViewHolder>(CartAdapterDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_CART_PRODUCT) {
            CartViewHolder.from(parent, cartHandler, handler)
        } else {
            PaginationButtonViewHolder.from(parent)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is CartAdapterItem.Product -> {
                if (holder is CartViewHolder) {
                    holder.bind(item.product)
                }
            }

            is CartAdapterItem.PaginationButton -> {
                if (holder is PaginationButtonViewHolder) {
                    holder.bind(cartHandler)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is CartAdapterItem.Product -> VIEW_TYPE_CART_PRODUCT
            is CartAdapterItem.PaginationButton -> VIEW_TYPE_PAGINATION_BUTTON
        }

    fun updateProduct(productItem: CartAdapterItem.Product) {
        val index =
            currentList.indexOfFirst {
                it is CartAdapterItem.Product && it.product.id == productItem.product.id
            }
        if (index != -1) {
            val newList =
                currentList.toMutableList().apply {
                    set(index, productItem)
                }
            submitList(newList)
        }
    }

    override fun getItemCount(): Int = currentList.size

    companion object {
        private val VIEW_TYPE_CART_PRODUCT = R.layout.product_item
        private val VIEW_TYPE_PAGINATION_BUTTON = R.layout.pagination_button_item
    }
}
