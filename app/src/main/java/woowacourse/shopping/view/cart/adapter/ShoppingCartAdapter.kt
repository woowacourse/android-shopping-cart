package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.cart.ShoppingCartActionHandler
import woowacourse.shopping.view.cart.ShoppingCartViewModel
import woowacourse.shopping.view.cart.adapter.viewholder.ShoppingCartViewHolder

class ShoppingCartAdapter(
    private val shoppingCartActionHandler: ShoppingCartActionHandler,
    private val countActionHandler: CountActionHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var cartItems: List<CartItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val view =
            ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(view, shoppingCartActionHandler, countActionHandler)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = cartItems[position]
        holder.bind(item)
    }

    fun updateCartItems(cartItems: List<CartItem>) {
        this.cartItems = cartItems
        val changedItemNextIndex = cartItems.size + 1
        if (cartItems.size != ShoppingCartViewModel.CART_ITEM_PAGE_SIZE) {
            notifyItemRangeRemoved(
                changedItemNextIndex,
                changedItemNextIndex + ShoppingCartViewModel.CART_ITEM_PAGE_SIZE,
            )
        }
        notifyItemRangeChanged(0, cartItems.size)
    }

    fun updateCartItem(
        productId: Long,
        newQuantity: Int,
    ) {
        val index = cartItems.indexOfFirst { it.product.id == productId }
        if (index != -1) {
            val updatedCartItem = cartItems[index].copy(quantity = newQuantity)
            cartItems =
                cartItems.toMutableList().apply {
                    set(index, updatedCartItem)
                }
            notifyItemChanged(index)
        }
    }
}
