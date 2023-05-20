package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.view.cart.viewholder.CartViewHolder

class CartAdapter(
    items: List<CartProductModel>,
    private val onCloseClick: (Long) -> Unit,
    private val onCheckedChangeListener: (Boolean) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            parent,
            onCloseClick = { onCloseClick(items[it].product.id) },
            onCheckedChangeListener = onCheckedChangeListener
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
