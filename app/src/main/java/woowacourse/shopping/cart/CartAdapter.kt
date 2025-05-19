package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.CartItem.PaginationButtonItem
import woowacourse.shopping.cart.CartItem.ProductItem

class CartAdapter(
    cartItems: List<CartItem>,
    private val onDeleteProductClick: DeleteProductClickListener,
    private val onPaginationButtonClick: PaginationButtonClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val cartItems: MutableList<CartItem> = cartItems.toMutableList()

    fun setCartItems(cartProducts: List<CartItem>) {
        notifyItemRangeRemoved(0, cartItems.size)
        cartItems.clear()
        cartItems.addAll(cartProducts)
        notifyItemRangeInserted(0, cartItems.size)
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
            is CartViewHolder -> holder.bind((cartItems[position] as ProductItem).productItem)
            is PaginationButtonViewHolder -> holder.bind(cartItems[position] as PaginationButtonItem)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (cartItems[position]) {
            is PaginationButtonItem -> PAGINATION_BUTTON
            is ProductItem -> CART_PRODUCT
        }

    override fun getItemCount(): Int = cartItems.size

    companion object {
        private const val CART_PRODUCT = 1
        private const val PAGINATION_BUTTON = 2
    }
}
