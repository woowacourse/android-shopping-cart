package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.CartItem

class CartAdapter(
    private val onClickExit: OnClickExit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private var cart: List<CartItem> = emptyList()

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
        holder.bind(onClickExit, cart[position])
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeCartItems(cartItems: List<CartItem>) {
        this.cart = cartItems
        notifyDataSetChanged()
    }
}
