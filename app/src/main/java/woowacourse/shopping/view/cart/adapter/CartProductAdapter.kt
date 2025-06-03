package woowacourse.shopping.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.view.cart.ShoppingCartEventHandler

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

    fun updateItems(newItems: List<CartProduct>) {
        val oldSize = items.size
        val newSize = newItems.size
        val minSize = minOf(oldSize, newSize)

        for (i in 0 until minSize) {
            val oldItem = items[i]
            val newItem = newItems[i]

            if (oldItem != newItem) {
                items[i] = newItem
                notifyItemChanged(i)
            }
        }

        if (newSize > oldSize) {
            val addedItems = newItems.subList(oldSize, newSize)
            items.addAll(addedItems)
            notifyItemRangeInserted(oldSize, addedItems.size)
        } else if (oldSize > newSize) {
            for (i in oldSize - 1 downTo newSize) {
                items.removeAt(i)
            }
            notifyItemRangeRemoved(newSize, oldSize - newSize)
        }
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
