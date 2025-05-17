package woowacourse.shopping.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.CartProduct

class CartProductAdapter(
    items: List<CartProduct> = emptyList(),
    private val eventHandler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder = CartProductViewHolder.from(parent, eventHandler)

    override fun getItemCount(): Int = items.size

    fun removeItem(product: CartProduct) {
        val position = items.indexOf(product)
        items.remove(product)
        notifyItemRemoved(position)
    }

    fun updateItems(newItems: List<CartProduct>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
