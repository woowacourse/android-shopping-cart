package woowacourse.shopping.view.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.OnProductListener

class ProductAdapter(
    private val productsEventListener: OnProductListener,
    private val loadEventListener: OnLoadEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val products = mutableListOf<Product>()
    private var hasNext: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT -> ProductViewHolder.from(parent, productsEventListener)
            LOAD_MORE -> LoadMoreViewHolder.from(parent, loadEventListener)
            else -> throw IllegalArgumentException()
        }

    override fun getItemCount(): Int = products.size + if (hasNext) LOAD_MORE_BUTTON_COUNT else 0

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (getItemViewType(position)) {
            PRODUCT -> (holder as ProductViewHolder).bind(products[position])
            LOAD_MORE -> {}
        }
    }

    override fun getItemViewType(position: Int): Int = if (hasNext && position == products.size) LOAD_MORE else PRODUCT

    fun addItems(
        newItems: List<Product>,
        hasNext: Boolean,
    ) {
        val startIndex = products.size
        products.addAll(newItems)
        this.hasNext = hasNext
        notifyItemRangeInserted(startIndex, newItems.size)

        if (!hasNext) {
            notifyItemRemoved(products.size)
        } else if (startIndex == 0) {
            notifyItemInserted(products.size)
        }
    }

    companion object {
        const val PRODUCT = 0
        const val LOAD_MORE = 1
        private const val LOAD_MORE_BUTTON_COUNT = 1
    }
}
