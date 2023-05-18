package woowacourse.shopping.ui.cart

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.viewHolder.CartViewHolder

class CartAdapter(
    cartItems: List<CartProductUIModel>,
    private val onItemClick: (ProductUIModel) -> Unit,
    private val onItemRemove: (Long) -> Unit,
    private val onCountChanged: (Long, Int) -> Unit,
    private val onCheckChanged: (Long, Boolean) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {

    private var cartItems: MutableList<CartProductUIModel> = cartItems.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.from(
            parent,
            onItemClick,
            onItemRemove,
            onCountChanged,
            onCheckChanged,
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

    fun updateItem(id: Long, checked: Boolean) {
        cartItems.find { it.product.id == id }?.isChecked?.set(checked)
        notifyItemChanged(cartItems.size)
    }
}
