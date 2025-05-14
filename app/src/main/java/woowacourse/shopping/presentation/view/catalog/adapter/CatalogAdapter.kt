package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class CatalogAdapter(
    products: List<ProductUiModel> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val products = products.toMutableList()
    var hasMore = false
        private set

    override fun getItemCount(): Int = products.size + if (hasMore) 1 else 0

    override fun getItemViewType(position: Int): Int =
        if (position == products.size && hasMore) {
            CatalogItem.CatalogType.LOAD_MORE.ordinal
        } else {
            CatalogItem.CatalogType.PRODUCT.ordinal
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            CatalogItem.CatalogType.LOAD_MORE.ordinal ->
                LoadMoreViewHolder.from(
                    parent,
                    eventListener,
                )

            CatalogItem.CatalogType.PRODUCT.ordinal -> ProductViewHolder.from(parent, eventListener)
            else -> throw IllegalArgumentException("Invalid view type")
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(products[position])
            is LoadMoreViewHolder -> {}
        }
    }

    fun updateProducts(
        products: List<ProductUiModel>,
        hasMore: Boolean,
    ) {
        val previousSize = itemCount
        this.products.clear()
        this.products.addAll(products)
        this.hasMore = hasMore

        notifyItemRangeInserted(previousSize, products.size)
    }

    interface CatalogEventListener {
        fun onProductClicked(product: ProductUiModel)

        fun onLoadMoreClicked()
    }
}
