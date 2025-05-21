package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.view.ItemCounterListener

class CartAdapter(
    cartItems: List<CartItem> = emptyList(),
    private val eventListener: CartEventListener,
    private val itemCounterListener: ItemCounterListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cartItems = cartItems.toMutableList()

    override fun getItemCount(): Int = cartItems.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, eventListener, itemCounterListener)

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(cartItems[position])
    }

    fun updateCartItems(cartItems: List<CartItem>) {
        this.cartItems.clear()
        this.cartItems.addAll(cartItems)

        notifyDataSetChanged()
    }

    fun updateItem(updatedItem: CartItem) {
        val index = cartItems.indexOfFirst { it.product.id == updatedItem.product.id }
        if (index != -1) {
            cartItems[index] = updatedItem
            notifyItemChanged(index)
        }
    }

    fun removeProduct(id: Long) {
        val index = cartItems.indexOfFirst { it.product.id == id }
        cartItems.removeAt(index)
        notifyItemRemoved(index)
    }

    interface CartEventListener {
        fun onProductDeletion(cartItem: CartItem)
    }
}
