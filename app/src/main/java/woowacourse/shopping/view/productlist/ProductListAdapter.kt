package woowacourse.shopping.view.productlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductModel

class ProductListAdapter(
    private val recentViewedProducts: List<ProductModel>,
    private val products: List<ProductModel>,
    private val onProductClick: (ProductModel) -> Unit,
    private val onShowMoreClick: () -> Unit,
    private val showCartItemsCountInMenu: () -> Unit,
    private val addProductCount: (ProductModel) -> Unit,
    private val plusProductCount: (ProductModel) -> Unit,
    private val minusProductCount: (ProductModel) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.of(
            parent,
            ProductListViewType.values()[viewType],
            onProductClick,
            onShowMoreClick,
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && isRecentViewedExist()) {
            ProductListViewType.RECENT_VIEWED_ITEM.ordinal
        } else if (position == itemCount - 1) {
            ProductListViewType.SHOW_MORE_ITEM.ordinal
        } else {
            ProductListViewType.PRODUCT_ITEM.ordinal
        }
    }

    override fun getItemCount(): Int =
        if (isRecentViewedExist()) products.size + 2 else products.size + 1

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder.RecentViewedViewHolder -> {
                holder.bind(recentViewedProducts, onProductClick)
            }

            is ProductViewHolder.ProductItemViewHolder -> {
                val convertPosition = if (isRecentViewedExist()) position - 1 else position
                holder.bind(
                    products[convertPosition],
                    showCartItemsCountInMenu,
                    addProductCount,
                    plusProductCount,
                    minusProductCount,
                )
            }

            is ProductViewHolder.ShowMoreViewHolder -> {
                return
            }
        }
    }

    private fun isRecentViewedExist(): Boolean = recentViewedProducts.isNotEmpty()
}
