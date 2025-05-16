package woowacourse.shopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Product

class CartAdapter(
    private val onClickHandler: CartViewHolder.OnClickHandler,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items: MutableList<Product> = mutableListOf<Product>()

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
        val item: Product = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(newItems: List<Product>) {
        val oldItems = items.toList()

        for ((position, newItem) in newItems.withIndex()) {
            val oldItem = oldItems.getOrNull(position)

            when {
                oldItem == null -> addItem(newItem)
                !isItemTheSame(oldItem, newItem) || !isContentTheSame(oldItem, newItem) -> updateItem(position, newItem)
            }
        }

        if (oldItems.size > newItems.size) {
            for (position in oldItems.lastIndex downTo newItems.size) {
                removeItem(position)
            }
        }
    }

    private fun isItemTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean = oldItem.id == newItem.id

    private fun isContentTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean = oldItem == newItem

    private fun updateItem(
        position: Int,
        newItem: Product,
    ) {
        if (position in 0 until items.size) {
            items[position] = newItem
            notifyItemChanged(position)
        }
    }

    private fun addItem(item: Product) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun removeItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
