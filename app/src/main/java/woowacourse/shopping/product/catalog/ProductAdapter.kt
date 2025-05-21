package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var products: List<ProductUiModel>,
    private val handler: CatalogEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var showLoadMoreButton = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == PRODUCT) {
            ProductViewHolder.from(parent, handler)
        } else {
            LoadButtonViewHolder.from(parent, handler)
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

    override fun getItemViewType(position: Int): Int =
        if (isLoadMoreButtonPosition(position)) {
            LOAD_BUTTON
        } else {
            PRODUCT
        }

    fun setData(newProducts: List<ProductUiModel>) {
        val oldSize = this.products.size
        val newSize = newProducts.size
        this.products = newProducts

        if (showLoadMoreButton) {
            notifyItemRemoved(oldSize)
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        } else {
            notifyItemRangeInserted(oldSize + 1, newSize - oldSize)
        }
    }

    override fun getItemCount(): Int = products.size + if (showLoadMoreButton) 1 else 0

    fun setLoadButtonVisible(visible: Boolean) {
        val previous = showLoadMoreButton
        showLoadMoreButton = visible
        if (previous != visible) {
            if (visible) {
                notifyItemInserted(products.size)
            } else {
                notifyItemRemoved(products.size)
            }
        }
    }

    fun isLoadMoreButtonPosition(position: Int): Boolean = showLoadMoreButton && position == products.size

    companion object {
        private const val PRODUCT = 1
        private const val LOAD_BUTTON = 2
    }
}
