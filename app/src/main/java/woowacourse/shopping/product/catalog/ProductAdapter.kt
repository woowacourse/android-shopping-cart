package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val products: List<ProductUiModel>,
    private val onProductClick: ProductClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == PRODUCT) {
            ProductViewHolder.from(parent, onProductClick)
        } else {
            LoadButtonViewHolder.from(parent)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(products[position])
            }

            is LoadButtonViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int = products.size

    companion object {
        private const val PRODUCT = 1
        private const val LOAD_BUTTON = 2
        private const val PAGE_SIZE = 20
    }
}
