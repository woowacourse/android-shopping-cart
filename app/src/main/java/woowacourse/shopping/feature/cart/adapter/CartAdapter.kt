package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.CartItem

class CartAdapter(
    private val onClickExit: OnClickExit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cart: MutableList<CartItem> = mutableListOf()

    init {
        setHasStableIds(true)
    }

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

    fun updateCart(newCart: List<CartItem>) {
        notifyItemRangeRemoved(0, cart.size)
        cart.removeAll(cart)
        cart.addAll(newCart)
        notifyItemRangeChanged(0, newCart.size)
    }
}
