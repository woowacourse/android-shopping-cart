package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.product.catalog.ProductUiModel

class CartAdapter(
    private var cartProducts: List<ProductUiModel>,
    private val cartEventHandler: CartEventHandler,
    private val page: () -> Int,
    private val isNextButtonEnabled: () -> Boolean,
    private val isPrevButtonEnabled: () -> Boolean,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_CART_PRODUCT) {
            CartViewHolder.from(parent, cartEventHandler)
        } else {
            PaginationButtonViewHolder.from(parent, cartEventHandler)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is CartViewHolder -> holder.bind(cartProducts[position])
            is PaginationButtonViewHolder ->
                holder.bind(
                    page = page(),
                    isNextButtonEnabled = isNextButtonEnabled(),
                    isPrevButtonEnabled = isPrevButtonEnabled(),
                )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == cartProducts.size) {
            return VIEW_TYPE_PAGINATION_BUTTON
        }
        return VIEW_TYPE_CART_PRODUCT
    }

    fun setData(newCartProducts: List<ProductUiModel>) {
        val oldSize = cartProducts.size
        val newSize = newCartProducts.size

        cartProducts = newCartProducts

        if (oldSize > 0) {
            notifyItemRangeRemoved(0, oldSize)
        }
        notifyItemRangeInserted(0, newSize)
    }

    private fun shouldShowPagination(): Boolean = isNextButtonEnabled() || isPrevButtonEnabled()

    override fun getItemCount(): Int = cartProducts.size + if (shouldShowPagination()) 1 else 0

    companion object {
        private val VIEW_TYPE_CART_PRODUCT = R.layout.product_item
        private val VIEW_TYPE_PAGINATION_BUTTON = R.layout.pagination_button_item
    }
}
