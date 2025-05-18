package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val cartViewModel: CartViewModel,
    private val onDeleteProductClick: DeleteProductClickListener,
    private val onPaginationButtonClick: PaginationButtonClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun setData(cartProducts: List<CartItem>) {
        this.cartItems = cartProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == CART_PRODUCT) {
            CartViewHolder.from(parent, onDeleteProductClick)
        } else {
            PaginationButtonViewHolder.from(parent, onPaginationButtonClick)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is CartViewHolder -> holder.bind((cartItems[position] as CartItem.ProductItem).productItem)
            is PaginationButtonViewHolder ->
                holder.bind(
                    page = cartViewModel.page.value ?: 1,
                    isNextButtonEnabled = cartViewModel.isNextButtonEnabled(),
                    isPrevButtonEnabled = cartViewModel.isPrevButtonEnabled(),
                )
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (cartItems[position]) {
            CartItem.PaginationButtonItem -> PAGINATION_BUTTON
            is CartItem.ProductItem -> CART_PRODUCT
        }

    override fun getItemCount(): Int = cartItems.size

    companion object {
        private const val CART_PRODUCT = 1
        private const val PAGINATION_BUTTON = 2
    }
}
