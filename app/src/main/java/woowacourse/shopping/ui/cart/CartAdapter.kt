package woowacourse.shopping.ui.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.ui.cart.viewHolder.CartViewHolder
import woowacourse.shopping.ui.cart.viewHolder.OnCartClickListener

class CartAdapter(
    cartItems: List<CartProductUIModel>,
    private val onCartClickListener: OnCartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private var cartItems: MutableList<CartProductUIModel> = cartItems.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.from(
            parent,
            onCartClickListener,
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

    fun updateItem(id: Long, count: Int) {
        val index = cartItems.indexOfFirst { it.product.id == id }
        cartItems[index] = cartItems[index].copy(count = count)
        notifyDataSetChanged()
    }

    fun updateChecked(id: Long, isChecked: Boolean) {
        val index = cartItems.indexOfFirst { it.product.id == id }
        cartItems[index] = cartItems[index].copy(isChecked = isChecked)
        notifyDataSetChanged()
    }
}
