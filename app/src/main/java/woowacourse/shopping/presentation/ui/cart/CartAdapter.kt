package woowacourse.shopping.presentation.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem

class CartAdapter(
    private val cartEventHandler: CartEventHandler,
    private val cartItemCountHandler: CartItemCountHandler,
) : RecyclerView.Adapter<CartViewHolder>() {
    private var cartItems: List<CartItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val cartItem = cartItems[position]
        return holder.bind(cartItem, cartEventHandler, cartItemCountHandler)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun loadData(cartItems: List<CartItem>) {
        this.cartItems = cartItems
        notifyDataSetChanged()
    }
}
