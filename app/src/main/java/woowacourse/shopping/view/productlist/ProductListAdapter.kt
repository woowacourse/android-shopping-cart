package woowacourse.shopping.view.productlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductModel

class ProductListAdapter(
    private val recentViewedProducts: List<ProductModel>?,
    private val products: List<ProductModel>,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<ProductViewHolder>() {
    fun interface OnItemClick {
        fun onClick(product: ProductModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.of(parent, ItemViewType.values()[viewType])
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && isRecentViewedExist()) {
            ItemViewType.RECENT_VIEWED_ITEM.ordinal
        } else {
            ItemViewType.PRODUCT_ITEM.ordinal
        }
    }

    override fun getItemCount(): Int = if (isRecentViewedExist()) products.size + 1 else products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder.RecentViewedViewHolder -> {
                holder.bind(recentViewedProducts, onItemClick)
            }
            is ProductViewHolder.ProductItemViewHolder -> {
                val convertPosition = if (isRecentViewedExist()) position - 1 else position
                holder.bind(products[convertPosition], onItemClick)
            }
        }
    }

    private fun isRecentViewedExist(): Boolean = recentViewedProducts != null
}
