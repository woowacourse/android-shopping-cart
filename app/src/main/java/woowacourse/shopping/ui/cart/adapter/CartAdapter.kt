package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartProduct

class CartAdapter(
    private val onClickHandler: CartViewHolder.OnClickHandler,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items: MutableList<CartProduct> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onClickHandler)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: CartProduct = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(newItems: List<CartProduct>) {
        val oldItems = items.toList()

        updateItems(newItems, oldItems)
        removeExceedingItems(oldItems, newItems)
    }

    private fun updateItems(
        newItems: List<CartProduct>,
        oldItems: List<CartProduct>,
    ) {
        for ((position, newItem) in newItems.withIndex()) {
            val oldItem = oldItems.getOrNull(position)

            when {
                oldItem == null -> addItem(newItem)
                !isContentTheSame(oldItem, newItem) -> replaceItem(position, newItem)
            }
        }
    }

    private fun isContentTheSame(
        oldItem: CartProduct,
        newItem: CartProduct,
    ): Boolean = oldItem == newItem

    private fun replaceItem(
        position: Int,
        newItem: CartProduct,
    ) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    private fun addItem(item: CartProduct) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun removeExceedingItems(
        oldItems: List<CartProduct>,
        newItems: List<CartProduct>,
    ) {
        if (oldItems.size > newItems.size) {
            for (position in oldItems.lastIndex downTo newItems.size) {
                removeItem(position)
            }
        }
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
