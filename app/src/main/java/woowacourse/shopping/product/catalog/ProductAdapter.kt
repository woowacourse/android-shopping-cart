package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.event.CatalogEventHandler
import woowacourse.shopping.product.catalog.viewHolder.LoadButtonViewHolder
import woowacourse.shopping.product.catalog.viewHolder.ProductViewHolder
import woowacourse.shopping.util.DiffCallback

class ProductAdapter(
    private val catalogHandler: CatalogEventHandler,
    private val quantityHandler: ProductQuantityHandler,
) : ListAdapter<ProductUiModel, RecyclerView.ViewHolder>(DiffCallback()) {
    private var showLoadMoreButton = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == PRODUCT) {
            ProductViewHolder.from(parent, catalogHandler, quantityHandler)
        } else {
            LoadButtonViewHolder.from(parent, catalogHandler)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (getItemViewType(position) == PRODUCT) {
            (holder as ProductViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (isLoadMoreButtonPosition(position)) {
            LOAD_BUTTON
        } else {
            PRODUCT
        }

    override fun getItemCount(): Int = currentList.size + if (showLoadMoreButton) 1 else 0

    fun setLoadButtonVisible(visible: Boolean) {
        val previous = showLoadMoreButton
        showLoadMoreButton = visible
        if (previous != visible) {
            if (visible) {
                notifyItemInserted(currentList.size)
            } else {
                notifyItemRemoved(currentList.size)
            }
        }
    }

    fun isLoadMoreButtonPosition(position: Int): Boolean = showLoadMoreButton && position == currentList.size

    companion object {
        private const val PRODUCT = 1
        private const val LOAD_BUTTON = 2
    }
}
