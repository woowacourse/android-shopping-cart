package woowacourse.shopping.view.cart.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.OnClickShoppingCart
import woowacourse.shopping.view.cart.adapter.viewholder.ShoppingCartViewHolder

class ShoppingCartAdapter(
    private val onClickShoppingCart: OnClickShoppingCart,
    private val loadLastItem: () -> Unit,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var cartItems: List<CartItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val view =
            ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(view, onClickShoppingCart)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item)

        if (position == itemCount - 1) {

            loadLastItem()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCartItems(addedCartItems: List<CartItem>) {
        val startPosition = cartItems.size
        cartItems = cartItems + addedCartItems
        notifyItemRangeInserted(startPosition, addedCartItems.size)

    }
}
