package woowacourse.shopping.view.productlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductModel

class ProductListAdapter(
    private val recentViewedProducts: List<ProductModel>,
    private val products: List<ProductModel>,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<ProductViewHolder>() {
    interface OnItemClick {
        fun onProductClick(product: ProductModel)
        fun onShowMoreClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.of(parent, ItemViewType.values()[viewType], onItemClick)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && isRecentViewedExist()) {
            ItemViewType.RECENT_VIEWED_ITEM.ordinal
        } else if (position == itemCount - 1) {
            ItemViewType.SHOW_MORE_ITEM.ordinal
        } else {
            ItemViewType.PRODUCT_ITEM.ordinal
        }
    }

    override fun getItemCount(): Int = if (isRecentViewedExist()) products.size + 2 else products.size + 1

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder.RecentViewedViewHolder -> {
                holder.bind(recentViewedProducts, onItemClick)
            }
            is ProductViewHolder.ProductItemViewHolder -> {
                val convertPosition = if (isRecentViewedExist()) position - 1 else position
                holder.bind(products[convertPosition])
            }
            is ProductViewHolder.ShowMoreViewHolder -> {
                return
            }
        }
    }

    private fun isRecentViewedExist(): Boolean = recentViewedProducts.isNotEmpty()
}
