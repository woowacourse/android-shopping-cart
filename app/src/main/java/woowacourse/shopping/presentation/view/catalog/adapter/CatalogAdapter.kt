package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CatalogItem
import woowacourse.shopping.presentation.model.CatalogItem.CatalogType
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener

class CatalogAdapter(
    products: List<CatalogItem> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val recentProductAdapter = RecentProductAdapter(emptyList(), eventListener)
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int = products[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (CatalogType.entries[viewType]) {
            CatalogType.RECENT_PRODUCT ->
                RecentProductContainerViewHolder.from(
                    parent,
                    recentProductAdapter,
                )

            CatalogType.PRODUCT -> ProductViewHolder.from(parent, eventListener)
            CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = products[position]
        when (holder) {
            is ProductViewHolder -> holder.bind(item as CatalogItem.ProductItem)
            is RecentProductContainerViewHolder -> holder.bind(item as CatalogItem.RecentProducts)
            is LoadMoreViewHolder -> Unit
        }
    }

    fun submitList(newItems: List<CatalogItem>) {
        newItems.forEachIndexed { index, newProduct ->
            val oldProduct = products.getOrNull(index)

            if (oldProduct == null) {
                products.add(index, newProduct)
                notifyItemInserted(index)
                return@forEachIndexed
            }

            if (oldProduct != newProduct) {
                products[index] = newProduct
                notifyItemChanged(index)
            }
        }
    }

    interface CatalogEventListener : QuantityChangeListener {
        fun onProductClicked(productId: Long)

        fun onLoadMoreClicked()

        fun onQuantitySelectorOpenButtonClicked(productId: Long)
    }
}
