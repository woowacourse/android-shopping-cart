package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class CatalogAdapter(
    items: List<CatalogItem> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<CatalogItem>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (CatalogItem.CatalogType.entries[viewType]) {
            CatalogItem.CatalogType.PRODUCT -> ProductViewHolder.from(parent, eventListener)
            CatalogItem.CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position] as CatalogItem.ProductItem)
            is LoadMoreViewHolder -> {}
        }
    }

    fun updateProducts(items: List<CatalogItem>) {
        val previousSize = itemCount
        this.items.clear()
        this.items.addAll(items)

        notifyItemRangeInserted(previousSize, items.size)
    }

    interface CatalogEventListener {
        fun onProductClicked(product: ProductUiModel)

        fun onLoadMoreClicked()
    }
}
