package woowacourse.shopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Product

class CartAdapter(
    private val itemRemoveClickListener: (Long) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cart: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, itemRemoveClickListener)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(cart[position])
    }

    override fun getItemCount(): Int = cart.size

    fun setData(products: List<Product>) {
        addItems(products)
        if (cart.size != CartViewModel.PAGE_SIZE) {
            notifyItemRangeRemoved(cart.size + OFFSET, CartViewModel.PAGE_SIZE - cart.size)
        }
        notifyItemRangeChanged(DEFAULT_POSITION, cart.size)
    }

    private fun addItems(products: List<Product>) {
        cart.apply {
            clear()
            addAll(products)
        }
    }

    companion object {
        private const val DEFAULT_POSITION = 0
        private const val OFFSET = 1
    }
}
