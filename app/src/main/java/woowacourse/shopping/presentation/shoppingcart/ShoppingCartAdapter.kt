package woowacourse.shopping.presentation.shoppingcart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.QuantitySelectorListener

class ShoppingCartAdapter(
    private val shoppingCartClickListener: ShoppingCartClickListener,
    private val quantitySelectorListener: QuantitySelectorListener,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var items: MutableList<Goods> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, shoppingCartClickListener, quantitySelectorListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<Goods>) {
        val oldItems = items.toList()

        items.clear()
        items.addAll(newItems)

        updateChangedItems(oldItems, newItems)
        notifyItemCountChanged(oldItems.size, newItems.size)
    }

    private fun updateChangedItems(
        oldItems: List<Goods>,
        newItems: List<Goods>,
    ) {
        val minSize = minOf(oldItems.size, newItems.size)

        for (index in 0 until minSize) {
            if (oldItems[index] != newItems[index]) {
                notifyItemChanged(index)
            }
        }
    }

    private fun notifyItemCountChanged(
        oldSize: Int,
        newSize: Int,
    ) {
        if (newSize > oldSize) {
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        } else if (oldSize > newSize) {
            notifyItemRangeRemoved(newSize, oldSize - newSize)
        }
    }
}
