package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.viewHolder.CartViewHolder
import woowacourse.shopping.model.CartProductUIModel

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onItemClick: (CartProductUIModel) -> Unit,
    private val onItemRemove: (Int) -> Unit,
    private val onIncreaseCount: (CartProductUIModel) -> Unit,
    private val onDecreaseCount: (CartProductUIModel) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.from(
            parent,
            onItemClick,
            onItemRemove,
            { onIncreaseCount(cartItems[it].cartProduct) },
            { onDecreaseCount(cartItems[it].cartProduct) },
            { notifyItemChanged(it) },
        )
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
