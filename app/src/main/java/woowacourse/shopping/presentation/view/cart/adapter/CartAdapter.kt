package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.cart.viewholder.CartViewHolder

class CartAdapter(
    private val items: List<CartModel>,
    private val onCountClick: (Long, Int) -> Unit,
    private val onCloseClick: (Long) -> Unit
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(parent) { onCloseClick(items[it].id) }
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position], onCountClick)
    }

    override fun getItemCount(): Int = items.size
}
