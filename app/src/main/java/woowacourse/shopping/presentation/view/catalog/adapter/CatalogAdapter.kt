package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem.CatalogType

class CatalogAdapter(
    products: List<CatalogItem> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int = products[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (CatalogType.entries[viewType]) {
            CatalogType.PRODUCT -> ProductViewHolder.from(parent, eventListener)
            CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(products[position] as CatalogItem.ProductItem)
            is LoadMoreViewHolder -> Unit
        }
    }

    fun appendProducts(products: List<CatalogItem>) {
        val previousSize = itemCount
        this.products.clear()
        this.products.addAll(products)

        notifyItemRangeInserted(previousSize, products.size)
    }

    fun updateQuantityAt(
        productId: Long,
        quantity: Int,
    ) {
        val foundItem = products.find { (it as? CatalogItem.ProductItem)?.product?.productId == productId } ?: return
        val oldItem = (foundItem as? CatalogItem.ProductItem)?.product ?: return
        val newItem = CatalogItem.ProductItem(oldItem.copy(quantity = quantity))
        val position = products.indexOf(foundItem)
        products[position] = newItem
        notifyItemChanged(position)
    }

    interface CatalogEventListener : QuantityChangeListener {
        fun onProductClicked(productId: Long)

        fun onLoadMoreClicked()

        fun onQuantitySelectorOpenButtonClicked(productId: Long)
    }
}
