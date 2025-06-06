package woowacourse.shopping.view.shoppingcart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.view.shoppingcart.ShoppingCartEventHandler

class ShoppingCartAdapter(
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val items: MutableList<CartProduct> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, handler)
    }

    fun submitList(newItems: List<CartProduct>) {
        val oldCount = items.size
        val newCount = newItems.size

        newItems.forEachIndexed { index, newItem ->
            val oldItem = items.getOrNull(index)
            if (oldItem == null) {
                items.add(newItem)
                notifyItemInserted(index)
            } else if (oldItem != newItem) {
                items[index] = newItem
                notifyItemChanged(index)
            }
        }

        if (newCount < oldCount) {
            repeat(oldCount - newCount) {
                items.removeAt(items.size - 1)
            }
            notifyItemRangeRemoved(newCount, oldCount - newCount)
        }
    }

    fun updateCartProduct(cartProduct: CartProduct) {
        items.forEachIndexed { index, item ->
            if (item.id == cartProduct.id) items[index] = cartProduct
            notifyItemChanged(index)
        }
    }
}
