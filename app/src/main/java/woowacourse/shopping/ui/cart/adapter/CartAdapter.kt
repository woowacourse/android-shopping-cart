package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class CartAdapter(
    private val onClickExit: OnClickExit,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cart: MutableList<CartItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(
            cart[position],
            onClickExit,
            onIncreaseProductQuantity,
            onDecreaseProductQuantity
        )
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeCartItems(cartItems: List<CartItem>) {
        cart.clear()
        cart.addAll(cartItems)
        notifyDataSetChanged()
    }

    fun replaceCartItem(replacedCartItem: CartItem) {
        val replacedCartItemPosition = cart.indexOfFirst { it.id == replacedCartItem.id }
        cart[replacedCartItemPosition] = replacedCartItem
        notifyItemChanged(replacedCartItemPosition)
    }

    fun removeCartItemById(removedCartItemId: Long) {
        val removedCartItemPosition = cart.indexOfFirst { it.id == removedCartItemId }
        cart.removeAt(removedCartItemPosition)
        notifyItemRemoved(removedCartItemPosition)
    }
}
