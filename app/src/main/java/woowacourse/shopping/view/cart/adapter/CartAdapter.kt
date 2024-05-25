package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.CartItemClickListener
import woowacourse.shopping.view.cart.CartViewModel.Companion.PAGE_SIZE
import woowacourse.shopping.view.cart.QuantityClickListener

class CartAdapter(
    private val cartItemClickListener: CartItemClickListener,
    private val quantityClickListener: QuantityClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private var cartItems: List<CartItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val cartItem = cartItems[position]
        return holder.bind(cartItem, cartItemClickListener, quantityClickListener)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun loadData(newCartItems: List<CartItem>) {
        val oldCartItems = this.cartItems
        this.cartItems = newCartItems

        val oldSize = oldCartItems.size
        val newSize = newCartItems.size

        if ((oldSize == 1 && newSize == PAGE_SIZE) || (oldSize == PAGE_SIZE && newSize >= 1)) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeChanged(0, newSize)
        } else {
            if (oldSize < newSize) {
                notifyItemInserted(oldSize + 1)
            } else {
                val removedItem = oldCartItems.find { !newCartItems.contains(it) }
                val removedPosition = oldCartItems.indexOf(removedItem)
                notifyItemRangeRemoved(removedPosition, oldSize - removedPosition)
            }
        }
    }

    fun updateCartItemQuantity(cartItem: CartItem) {
        val position = cartItems.indexOfFirst { it.id == cartItem.id }
        if (position != -1) {
            (cartItems as MutableList)[position] = cartItem
            notifyItemChanged(position)
        }
    }
}
