package woowacourse.shopping.view.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.cart.CartItem

class ProductsAdapter(
    private val cartItems: MutableList<CartItem> = mutableListOf(),
    private val productClickListener: (CartItem) -> Unit,
    private val openQuantitySelectListener: () -> Boolean,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val openedQuantitySelectors = mutableSetOf<Long>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder =
        ProductViewHolder.from(
            parent,
            productClickListener,
            openQuantitySelectListener,
            quantitySelectButtonListener,
        )

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        val cartItem = cartItems[position]
        val isQuantitySelectorOpened = openedQuantitySelectors.contains(cartItem.product.id)
        holder.bind(cartItems[position], isQuantitySelectorOpened)
    }

    fun updateProductsView(list: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    fun updateQuantityView(productId: Long) {
        val position = cartItems.indexOfFirst { it.product.id == productId }
        openedQuantitySelectors.add(productId)
        notifyItemChanged(position)
    }
}
