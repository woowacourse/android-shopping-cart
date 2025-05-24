package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.cart.event.CartEventHandler
import woowacourse.shopping.cart.viewHolder.CartViewHolder
import woowacourse.shopping.cart.viewHolder.PaginationButtonViewHolder
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel

class CartAdapter(
    private var cartProducts: List<ProductUiModel>,
    private val cartHandler: CartEventHandler,
    private val handler: ProductQuantityHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        when (holder) {
            is CartViewHolder -> holder.bind(cartProducts[position])
            is PaginationButtonViewHolder ->
                holder.bind(cartHandler)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == cartProducts.size) {
            return VIEW_TYPE_PAGINATION_BUTTON
        }
        return VIEW_TYPE_CART_PRODUCT
    }

    fun setData(newCartProducts: List<ProductUiModel>) {
        val hadPagination = shouldShowPagination()
        val oldSize = cartProducts.size
        cartProducts = newCartProducts
        val hasPagination = shouldShowPagination()
        val newSize = cartProducts.size

        val oldTotalCount = oldSize + if (hadPagination) 1 else 0
        val newTotalCount = newSize + if (hasPagination) 1 else 0

        if (oldTotalCount != newTotalCount) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, newTotalCount)
        }
    }

    private fun shouldShowPagination(): Boolean = cartHandler.isNextButtonEnabled() || cartHandler.isPrevButtonEnabled()

    override fun getItemCount(): Int = cartProducts.size + if (shouldShowPagination()) 1 else 0

    fun updateProduct(product: ProductUiModel) {
        val index = cartProducts.indexOfFirst { it.id == product.id }
        if (index != -1) {
            cartProducts =
                cartProducts.toMutableList().apply {
                    set(index, product)
                }
            notifyItemChanged(index)
        }
    }

    companion object {
        private val VIEW_TYPE_CART_PRODUCT = R.layout.product_item
        private val VIEW_TYPE_PAGINATION_BUTTON = R.layout.pagination_button_item
    }
}
