package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    products: List<ProductUiModel>,
    private val productActionListener: ProductActionListener,
    private val quantityControlListener: QuantityControlListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val products: MutableList<CatalogItem> =
        products.map { CatalogItem.ProductItem(it) }.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_PRODUCT) {
            ProductViewHolder.from(parent, productActionListener, quantityControlListener)
        } else {
            LoadButtonViewHolder.from(parent, productActionListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind((products[position] as CatalogItem.ProductItem).productItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (products[position]) {
            CatalogItem.LoadMoreButtonItem -> VIEW_TYPE_LOAD_MORE
            is CatalogItem.ProductItem -> VIEW_TYPE_PRODUCT
        }

    fun setItems(items: List<CatalogItem>) {
        products.clear()
        products.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItem(product: ProductUiModel) {
        val index: Int =
            products.indexOfFirst { (it as CatalogItem.ProductItem).productItem.name == product.name }
        products[index] = CatalogItem.ProductItem(product)
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int = products.size

    companion object {
        private const val VIEW_TYPE_PRODUCT = 1
        private const val VIEW_TYPE_LOAD_MORE = 2
    }
}
