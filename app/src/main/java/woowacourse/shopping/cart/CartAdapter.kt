package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.product.catalog.ProductUiModel

class CartAdapter(
    private var cartProducts: List<ProductUiModel>,
    private val cartViewModel: CartViewModel,
    private val onDeleteProductClick: DeleteProductClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == CART_PRODUCT) {
            CartViewHolder.from(parent, onDeleteProductClick)
        } else {
            PaginationButtonViewHolder.from(parent, cartViewModel)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is CartViewHolder -> holder.bind(cartProducts[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == cartProducts.size) {
            return PAGINATION_BUTTON
        }
        return CART_PRODUCT
    }

    fun setData(cartProducts: List<ProductUiModel>) {
        this.cartProducts = cartProducts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        if (cartProducts.size % 5 == 0 &&
            cartProducts.isNotEmpty()
        ) {
            cartProducts.size + 1
        } else {
            cartProducts.size
        }

    companion object {
        private const val CART_PRODUCT = 1
        private const val PAGINATION_BUTTON = 2
    }
}
