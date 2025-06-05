package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.feature.QuantityChangeListener

class CartAdapter(
    private val cartClickListener: CartViewHolder.CartClickListener,
    private val quantityChangeListener: QuantityChangeListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cartItems: MutableList<CartItem> = mutableListOf()

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }

    fun setItems(newCartItems: List<CartItem>) {
        val oldItems = cartItems.toList()
        cartItems.clear()
        cartItems.addAll(newCartItems)

        if (newCartItems.size < oldItems.size) {
            notifyItemRangeRemoved(newCartItems.size, oldItems.size - newCartItems.size)
        }

        newCartItems.forEachIndexed { index, newCartItem ->
            if (index < oldItems.size && newCartItem != oldItems[index]) {
                if (newCartItem.goods == oldItems[index].goods &&
                    newCartItem.quantity != oldItems[index].quantity
                ) {
                    updateItemQuantity(index)
                } else {
                    notifyItemChanged(index)
                }
            }
        }

        if (newCartItems.size > oldItems.size) {
            notifyItemRangeInserted(oldItems.size, newCartItems.size - oldItems.size)
        }
    }

    private fun updateItemQuantity(position: Int) {
        notifyItemChanged(position, QUANTITY_CHANGED_PAYLOAD)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        binding.cartClickListener = cartClickListener
        binding.quantityChangeListener = quantityChangeListener
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: CartItem = cartItems[position]
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val payload = payloads[0]
            if (payload == QUANTITY_CHANGED_PAYLOAD) {
                holder.bind(cartItems[position])
                holder.binding.quantitySelector.cartItem = cartItems[position]
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size

    companion object {
        private const val QUANTITY_CHANGED_PAYLOAD = "quantity_changed"
    }
}
