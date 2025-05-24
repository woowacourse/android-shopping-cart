package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.event.CatalogEventHandler
import woowacourse.shopping.product.catalog.viewHolder.LoadButtonViewHolder
import woowacourse.shopping.product.catalog.viewHolder.ProductViewHolder

class ProductAdapter(
    private var products: List<ProductUiModel>,
    private val catalogHandler: CatalogEventHandler,
    private val quantityHandler: ProductQuantityHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        val oldProducts = this.products
        this.products = newProducts

        val diffResult =
            DiffUtil.calculateDiff(
                object : DiffUtil.Callback() {
                    override fun getOldListSize() = oldProducts.size

                    override fun getNewListSize() = newProducts.size

                    override fun areItemsTheSame(
                        oldPos: Int,
                        newPos: Int,
                    ) = oldProducts[oldPos].id == newProducts[newPos].id

                    override fun areContentsTheSame(
                        oldPos: Int,
                        newPos: Int,
                    ) = oldProducts[oldPos] == newProducts[newPos]

                    override fun getChangePayload(
                        oldPos: Int,
                        newPos: Int,
                    ): Any? = null
                },
            )

        diffResult.dispatchUpdatesTo(this)
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
