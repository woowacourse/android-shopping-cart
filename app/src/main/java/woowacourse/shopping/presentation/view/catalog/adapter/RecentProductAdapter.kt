package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class RecentProductAdapter(
    products: List<ProductUiModel>,
    private val eventListener: CatalogAdapter.CatalogEventListener,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder = RecentProductViewHolder.from(parent, eventListener)

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateList(newList: List<ProductUiModel>) {
        val oldItemsMap = products.associateBy { it.id }.toMutableMap()
        val newItemsMap = newList.associateBy { it.id }

        handleRemovals(oldItemsMap, newItemsMap)
        handleInsertionsAndUpdates(newList, oldItemsMap)
        reorderItems(newList)
    }

    private fun handleRemovals(
        oldItemsMap: MutableMap<Long, ProductUiModel>,
        newItemsMap: Map<Long, ProductUiModel>,
    ) {
        val toRemove = oldItemsMap.keys - newItemsMap.keys
        toRemove.forEach { id ->
            val index = products.indexOfFirst { it.id == id }
            if (index != INDEX_NOT_FOUND) {
                products.removeAt(index)
                notifyItemRemoved(index)
            }
        }
    }

    private fun handleInsertionsAndUpdates(
        newList: List<ProductUiModel>,
        oldItemsMap: MutableMap<Long, ProductUiModel>,
    ) {
        newList.forEachIndexed { index, newItem ->
            val oldItem = oldItemsMap.remove(newItem.id)

            if (oldItem == null) {
                products.add(index, newItem)
                notifyItemInserted(index)
                return@forEachIndexed
            }

            val oldIndex = products.indexOfFirst { it.id == newItem.id }
            if (oldItem != newItem && oldIndex != INDEX_NOT_FOUND) {
                products[oldIndex] = newItem
                notifyItemChanged(oldIndex)
            }
        }
    }

    private fun reorderItems(newList: List<ProductUiModel>) {
        newList.forEachIndexed { newIndex, item ->
            val currentIndex = products.indexOfFirst { it.id == item.id }
            if (currentIndex != INDEX_NOT_FOUND && currentIndex != newIndex) {
                val movedItem = products.removeAt(currentIndex)
                products.add(newIndex, movedItem)
                notifyItemMoved(currentIndex, newIndex)
            }
        }
    }

    companion object {
        private const val INDEX_NOT_FOUND = -1
    }
}
