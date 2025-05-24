package woowacourse.shopping.view.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.products.QuantitySelectButtonListener

class CartAdapter(
    private val itemsInCart: MutableList<CartItem> = mutableListOf(),
    private val onProductRemoveClickListener: (CartItem) -> Unit,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, quantitySelectButtonListener)

    override fun getItemCount(): Int = itemsInCart.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.removeProductButton.setOnClickListener {
            onProductRemoveClickListener(itemsInCart[position])
        }
        holder.bind(itemsInCart[position])
    }

    fun updateProductsView(items: List<CartItem>) {
        val oldSize = itemsInCart.size
        itemsInCart.clear()
        notifyItemRangeRemoved(0, oldSize)

        itemsInCart.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }
}
