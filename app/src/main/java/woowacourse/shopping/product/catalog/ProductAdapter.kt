package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var products: List<ProductUiModel>,
    private val onProductClick: ProductClickListener,
    private val onLoadButtonClick: LoadButtonClickListener,
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

    override fun getItemViewType(position: Int): Int {
        if (position == products.size && products.size % PRODUCT_SIZE_LIMIT == 0) {
            return LOAD_BUTTON
        }
        return PRODUCT
    }

    fun setData(products: List<ProductUiModel>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (products.size % PRODUCT_SIZE_LIMIT == 0) products.size + 1 else products.size

    companion object {
        private const val PRODUCT = 1
        private const val LOAD_BUTTON = 2
        private const val PRODUCT_SIZE_LIMIT = 20
    }
}
