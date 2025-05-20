package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var products: List<ProductUiModel>,
    private val onProductClick: ProductClickListener,
    private val onLoadButtonClick: LoadButtonClickListener,
    private val isLoadButtonEnabled: () -> Boolean,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == PRODUCT) {
            ProductViewHolder.from(parent, onProductClick)
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

    override fun getItemViewType(position: Int): Int =
        when {
            position < products.size -> PRODUCT
            position == products.size && isLoadButtonEnabled() -> LOAD_BUTTON
            else -> PRODUCT
        }

    fun setData(newProducts: List<ProductUiModel>) {
        val oldSize = this.products.size
        val newSize = newProducts.size
        this.products = newProducts

        if (isLoadButtonEnabled()) {
            notifyItemRemoved(oldSize)
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        } else {
            notifyItemRangeInserted(oldSize + 1, newSize - oldSize)
        }
    }

    override fun getItemCount(): Int = products.size + if (isLoadButtonEnabled()) 1 else 0

    companion object {
        private const val PRODUCT = 1
        private const val LOAD_BUTTON = 2
    }
}
