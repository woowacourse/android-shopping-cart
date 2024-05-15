package woowacourse.shopping.view.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.OnClickShoppingCart
import woowacourse.shopping.view.cart.adapter.viewholder.ShoppingCartViewHolder

class ShoppingCartAdapter(
    private val onClickShoppingCart: OnClickShoppingCart,
): RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var cartItems: List<CartItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val view = ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShoppingCartViewHolder(view,onClickShoppingCart)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCartItems(cartItems: List<CartItem>){
        this.cartItems = cartItems
        notifyDataSetChanged()
    }
}
