package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    products: List<ProductUiModel>,
    private val totalDataSize: Int,
    val onProductClick: ProductClickListener,
    private val onLoadButtonClick: LoadButtonClickListener,
    private val onQuantityAddClickListener: QuantityAddClickListener,
    private val onQuantityControlClickListener: QuantityControlClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val products: MutableList<ProductUiModel> = products.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_PRODUCT) {
            ProductViewHolder.from(
                parent,
                onProductClick,
                onQuantityAddClickListener,
                onQuantityControlClickListener
            )
        } else {
            LoadButtonViewHolder.from(parent, onLoadButtonClick)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(products[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == products.size) {
            return VIEW_TYPE_LOAD_MORE
        }
        return VIEW_TYPE_PRODUCT
    }

    fun setData(products: List<ProductUiModel>) {
        val positionStart = this.products.size
        val itemCount = products.size - positionStart
        this.products.clear()
        this.products.addAll(products)

        notifyItemRangeInserted(positionStart + 1, itemCount)
    }

    fun updateProduct(product: ProductUiModel) {
        val index: Int = products.indexOfFirst { it.name == product.name }
        products[index] = product
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int = products.size + if (isLoadable()) 1 else 0

    private fun isLoadable(): Boolean = products.size < totalDataSize

    companion object {
        private const val VIEW_TYPE_PRODUCT = 1
        private const val VIEW_TYPE_LOAD_MORE = 2
        private const val PRODUCT_SIZE_LIMIT = 20
    }
}
