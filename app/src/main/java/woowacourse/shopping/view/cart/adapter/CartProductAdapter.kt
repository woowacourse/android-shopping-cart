package woowacourse.shopping.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.ShoppingProduct

class CartProductAdapter(
    items: List<ShoppingProduct> = emptyList(),
    private val eventHandler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder = CartProductViewHolder.from(parent, eventHandler)

    override fun getItemCount(): Int = items.size

    fun removeItem(product: ShoppingProduct) {
        notifyItemRemoved(items.indexOf(product))
        items.remove(product)
    }

    fun updateItems(newItems: List<ShoppingProduct>) {
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
