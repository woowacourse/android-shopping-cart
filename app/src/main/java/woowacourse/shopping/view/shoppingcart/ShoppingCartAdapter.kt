package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.CartItem

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val cartItems: MutableList<CartItem> = mutableListOf()

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = cartItems[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, handler)
    }

    fun updateCartItems(newItems: List<CartItem>) {
        val oldCount = itemCount
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyItemRangeChanged(0, newItems.size.coerceAtLeast(oldCount))
    }
}
