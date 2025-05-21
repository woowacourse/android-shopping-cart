package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.product.catalog.ProductUiModel

class CartAdapter(
    private var cartProducts: List<ProductUiModel>,
    private val handler: CartEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_CART_PRODUCT) {
            CartViewHolder.from(parent, handler)
        } else {
            PaginationButtonViewHolder.from(parent, handler)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is CartViewHolder -> holder.bind(cartProducts[position])
            is PaginationButtonViewHolder ->
                holder.bind(
                    page = handler.getPage(),
                    isNextButtonEnabled = handler.isNextButtonEnabled(),
                    isPrevButtonEnabled = handler.isPrevButtonEnabled(),
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

    private fun shouldShowPagination(): Boolean = handler.isNextButtonEnabled() || handler.isPrevButtonEnabled()

    override fun getItemCount(): Int = cartProducts.size + if (shouldShowPagination()) 1 else 0

    companion object {
        private val VIEW_TYPE_CART_PRODUCT = R.layout.product_item
        private val VIEW_TYPE_PAGINATION_BUTTON = R.layout.pagination_button_item
    }
}
