package woowacourse.shopping.ui.cart.cartAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.CartItemViewHolder
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.CartViewHolder
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.NavigationViewHolder

class CartAdapter(
    private val cartItems: List<CartItemType>,
    private val cartListener: CartListener
) : RecyclerView.Adapter<CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return when (viewType) {
            CartItemType.TYPE_ITEM -> CartViewHolder.from(parent, cartListener)
            CartItemType.TYPE_FOOTER -> NavigationViewHolder.from(parent, cartListener)
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        return holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return cartItems[position].viewType
    }
}
