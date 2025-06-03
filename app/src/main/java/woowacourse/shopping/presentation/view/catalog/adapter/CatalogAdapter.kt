package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.view.ItemCounterListener

class CatalogAdapter(
    private val eventListener: CatalogEventListener,
    private val itemCounterListener: ItemCounterListener,
) : ListAdapter<CatalogItem, RecyclerView.ViewHolder>(itemDiffCallback) {
    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (CatalogItem.CatalogType.entries[viewType]) {
            CatalogItem.CatalogType.RECENT -> RecentProductViewHolder.from(parent)
            CatalogItem.CatalogType.PRODUCT ->
                ProductViewHolder.from(parent, eventListener, itemCounterListener)

            CatalogItem.CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is CatalogItem.RecentProductItem -> {
                (holder as RecentProductViewHolder).bind(item.products)
            }

            is CatalogItem.ProductItem -> {
                (holder as ProductViewHolder).bind(item)
            }

            is CatalogItem.LoadMoreItem -> {
            }
        }
    }

    fun updateItem(productUiModel: ProductUiModel) {
        val currentList = currentList.toMutableList()
        val index =
            currentList.indexOfFirst {
                it is CatalogItem.ProductItem && it.product.id == productUiModel.id
            }
        if (index != -1) {
            currentList[index] = CatalogItem.ProductItem(productUiModel)
            submitList(currentList)
        }
    }

    fun removeItemAmount(productId: Long) {
        val updatedList =
            currentList.map {
                if (it is CatalogItem.ProductItem && it.product.id == productId) {
                    CatalogItem.ProductItem(it.product.copy(amount = 0))
                } else {
                    it
                }
            }
        submitList(updatedList)
    }

    companion object {
        private val itemDiffCallback =
            object : DiffUtil.ItemCallback<CatalogItem>() {
                override fun areItemsTheSame(
                    oldItem: CatalogItem,
                    newItem: CatalogItem,
                ): Boolean =
                    when {
                        oldItem is CatalogItem.ProductItem && newItem is CatalogItem.ProductItem ->
                            oldItem.product.id == newItem.product.id

                        oldItem is CatalogItem.RecentProductItem && newItem is CatalogItem.RecentProductItem ->
                            true

                        oldItem is CatalogItem.LoadMoreItem && newItem is CatalogItem.LoadMoreItem ->
                            true

                        else -> false
                    }

                override fun areContentsTheSame(
                    oldItem: CatalogItem,
                    newItem: CatalogItem,
                ): Boolean = oldItem == newItem
            }
    }

    interface CatalogEventListener {
        fun onProductClicked(product: ProductUiModel)

        fun onLoadMoreClicked()

        fun onInitialAddToCartClicked(product: ProductUiModel)
    }
}
