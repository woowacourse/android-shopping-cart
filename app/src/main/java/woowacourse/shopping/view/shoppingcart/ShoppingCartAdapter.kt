package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.CartProduct

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val cartProducts: MutableList<CartProduct> = mutableListOf()

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = cartProducts[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, handler)
    }

    fun updateCartItems(newItems: List<CartProduct>) {
        val oldCount = itemCount
        cartProducts.clear()
        cartProducts.addAll(newItems)
        notifyItemRangeChanged(0, newItems.size.coerceAtLeast(oldCount))
    }
}
