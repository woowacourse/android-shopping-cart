package woowacourse.shopping.ui.products.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHistoryProductBinding
import woowacourse.shopping.domain.model.HistoryProduct

class HistoryProductAdapter(
    private val onClickHandler: HistoryProductViewHolder.OnClickHandler,
) : RecyclerView.Adapter<HistoryProductViewHolder>() {
    private val items: MutableList<HistoryProduct> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryProductViewHolder {
        val binding = ItemHistoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryProductViewHolder(binding, onClickHandler)
    }

    override fun onBindViewHolder(
        holder: HistoryProductViewHolder,
        position: Int,
    ) {
        val item: HistoryProduct = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(newItems: List<HistoryProduct>) {
        val oldItems = items.toList()

        updateItems(newItems, oldItems)
        removeExceedingItems(oldItems, newItems)
    }

    private fun updateItems(
        newItems: List<HistoryProduct>,
        oldItems: List<HistoryProduct>,
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
        oldItem: HistoryProduct,
        newItem: HistoryProduct,
    ): Boolean = oldItem == newItem

    private fun replaceItem(
        position: Int,
        newItem: HistoryProduct,
    ) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    private fun addItem(item: HistoryProduct) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun removeExceedingItems(
        oldItems: List<HistoryProduct>,
        newItems: List<HistoryProduct>,
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
