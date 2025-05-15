package woowacourse.shopping.view.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.OnProductListener

class ProductAdapter(
    private var products: List<Product>,
    private val eventListener: OnProductListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT -> ProductViewHolder.from(parent, eventListener)
            LOAD_MORE -> LoadMoreViewHolder.from(parent)
            else -> throw IllegalArgumentException()
        }

    override fun getItemCount(): Int =
        if (products.size % PRODUCT_SIZE_LIMIT == 0) products.size + LOAD_MORE_BUTTON_COUNT else products.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (getItemViewType(position)) {
            PRODUCT -> (holder as ProductViewHolder).bind(products[position])
            LOAD_MORE -> {}
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position % PRODUCT_SIZE_LIMIT == 0 && position == products.size) {
            LOAD_MORE
        } else {
            PRODUCT
        }

    fun setItems(newItems: List<Product>) {
        products = newItems
        notifyDataSetChanged()
    }

    companion object {
        const val PRODUCT = 0
        const val LOAD_MORE = 1
        private const val PRODUCT_SIZE_LIMIT = 20
        private const val LOAD_MORE_BUTTON_COUNT = 1
    }
}
