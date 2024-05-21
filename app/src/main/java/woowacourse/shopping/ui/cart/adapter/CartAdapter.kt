package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Cart
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

class CartAdapter(
    private val itemRemoveClickListener: (Long) -> Unit,
    private val plusCountClickListener: (Long) -> Unit,
    private val minusCountClickListener: (Long) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cart: MutableList<Cart> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(
            binding,
            itemRemoveClickListener,
            plusCountClickListener,
            minusCountClickListener,
        )
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(cart[position])
    }

    override fun getItemCount(): Int = cart.size

    fun setData(carts: List<Cart>) {
        addItems(carts)
        if (cart.size != CartViewModel.PAGE_SIZE) {
            notifyItemRangeRemoved(cart.size + OFFSET, CartViewModel.PAGE_SIZE - cart.size)
        }
        notifyItemRangeChanged(DEFAULT_POSITION, cart.size)
    }

    private fun addItems(carts: List<Cart>) {
        cart.apply {
            clear()
            addAll(carts)
        }
    }

    companion object {
        private const val DEFAULT_POSITION = 0
        private const val OFFSET = 1
    }
}
