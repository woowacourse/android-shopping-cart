package woowacourse.shopping.view.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.product.OnQuantityControlListener

class SelectedProductAdapter(
    private val onQuantityControlListener: OnQuantityControlListener,
    private val removeEventListener: OnRemoveProductListener,
) : RecyclerView.Adapter<SelectedProductViewHolder>() {
    private val shoppingProducts = mutableListOf<ShoppingProduct>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SelectedProductViewHolder = SelectedProductViewHolder.from(parent, onQuantityControlListener, removeEventListener)

    override fun getItemCount(): Int = shoppingProducts.size

    private fun updateItems(newItems: List<ShoppingProduct>) {
        val oldItems = shoppingProducts.toList()

        val commonSize = minOf(oldItems.size, newItems.size)
        for (i in 0 until commonSize) {
            if (oldItems[i] != newItems[i]) {
                shoppingProducts[i] = newItems[i]
                notifyItemChanged(i)
            }
        }
    }

    private fun removeItem(newItems: List<ShoppingProduct>) {
        val newIds = newItems.map { it.productId }.toSet()
        var index = 0

        val iterator = shoppingProducts.listIterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.productId !in newIds) {
                iterator.remove()
                notifyItemRemoved(index)
            } else {
                index++
            }
        }
    }

    private fun nextItem(newItems: List<ShoppingProduct>) {
        if (!this.shoppingProducts.any { it in newItems }) {
            notifyItemRangeRemoved(0, shoppingProducts.size)
            shoppingProducts.clear()
            shoppingProducts.addAll(newItems)
            notifyItemRangeInserted(0, newItems.size)
        } else {
            removeItem(newItems)
        }
    }

    fun updateShoppingProductItems(newItems: List<ShoppingProduct>) {
        if (this.shoppingProducts.size == newItems.size) {
            updateItems(newItems)
        } else if (this.shoppingProducts.size < newItems.size) {
            appendItem(newItems)
        } else {
            nextItem(newItems)
        }
    }

    private fun appendItem(newItems: List<ShoppingProduct>) {
        notifyItemRangeRemoved(0, shoppingProducts.size)
        shoppingProducts.clear()
        shoppingProducts.addAll(newItems)
        notifyItemRangeInserted(0, newItems.size)
    }

    override fun onBindViewHolder(
        holder: SelectedProductViewHolder,
        position: Int,
    ) {
        holder.bind(shoppingProducts[position])
    }
}
