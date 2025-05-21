package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem

class CartAdapter(
    private val cartClickListener: CartViewHolder.CartClickListener,
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
        newCartItems.forEachIndexed { index, newGoods ->
            if (index < oldItems.size && newGoods != oldItems[index]) {
                notifyItemChanged(index)
            }
        }
        if (newCartItems.size > oldItems.size) {
            notifyItemRangeInserted(oldItems.size, newCartItems.size - oldItems.size)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        binding.cartClickListener = cartClickListener
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: CartItem = cartItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cartItems.size
}
