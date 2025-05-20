package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.view.core.base.BaseViewHolder
import woowacourse.shopping.view.main.vm.LoadState
import woowacourse.shopping.view.main.vm.ProductUiState

class ProductAdapter(
    items: List<ProductRvItems>,
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {
    private val items: MutableList<ProductRvItems> = items.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return when (ProductRvItems.ViewType.entries[viewType]) {
            ProductRvItems.ViewType.VIEW_TYPE_PRODUCT ->
                ProductViewHolder(
                    parent,
                    handler,
                )

            ProductRvItems.ViewType.VIEW_TYPE_LOAD ->
                LoadViewHolder(
                    parent,
                    handler,
                )
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding>,
        position: Int,
    ) {
        when (val item = items[position]) {
            is ProductRvItems.ProductItem -> (holder as ProductViewHolder).bind(item)
            is ProductRvItems.LoadItem -> (holder as LoadViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    fun submitList(newUiState: ProductUiState) {
        removeLoadItem()

        newUiState
            .items
            .forEachIndexed { index, newState ->
                val existingItem = items.getOrNull(index) as? ProductRvItems.ProductItem
                val newItem = newState.toProductRvItems()
                applyItemChange(existingItem, newItem, index)
            }

        generateLoad(newUiState.load)
    }

    fun applyItemChange(
        existingItem: ProductRvItems.ProductItem?,
        newItem: ProductRvItems.ProductItem,
        index: Int,
    ) {
        when {
            existingItem == null -> {
                items.add(newItem)
                notifyItemInserted(index)
            }

            !areContentsTheSame(existingItem, newItem) -> {
                items[index] = newItem
                notifyItemChanged(index)
            }

            else -> return
        }
    }

    private fun areContentsTheSame(
        oldItem: ProductRvItems.ProductItem,
        newItem: ProductRvItems.ProductItem,
    ): Boolean {
        return oldItem.item == newItem.item &&
            oldItem.quantity == newItem.quantity &&
            oldItem.quantityVisible == newItem.quantityVisible
    }

    private fun generateLoad(load: LoadState) {
        if (load is LoadState.CanLoad) addLoadItem()
    }

    private fun addLoadItem() {
        items.add(ProductRvItems.LoadItem)
        notifyItemInserted(items.size - 1)
    }

    private fun removeLoadItem() {
        val index = items.indexOfFirst { it is ProductRvItems.LoadItem }
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
