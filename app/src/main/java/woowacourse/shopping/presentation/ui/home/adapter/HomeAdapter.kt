package woowacourse.shopping.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.HomeData
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.RecentlyViewed
import woowacourse.shopping.presentation.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.model.ShowMoreItem
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.PRODUCT
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.RECENTLY_VIEWED
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ProductViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ShowMoreViewHolder

class HomeAdapter(
    private val productClickListener: ProductClickListener,
    private val clickShowMore: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<HomeData>()
    private val recentlyViewedAdapter = RecentlyViewedProductAdapter(productClickListener)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].viewType) {
            PRODUCT -> PRODUCT.ordinal
            RECENTLY_VIEWED -> RECENTLY_VIEWED.ordinal
            SHOW_MORE -> SHOW_MORE.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PRODUCT.ordinal -> {
                ProductViewHolder(
                    ProductViewHolder.getView(parent),
                    productClickListener,
                )
            }
            RECENTLY_VIEWED.ordinal -> {
                RecentlyViewedViewHolder(
                    RecentlyViewedViewHolder.getView(parent),
                    recentlyViewedAdapter,
                )
            }
            SHOW_MORE.ordinal -> {
                ShowMoreViewHolder(ShowMoreViewHolder.getView(parent)) { showMoreProducts() }
            }
            else -> throw IllegalArgumentException("HomeAdapter의 아이템 viewType이 이상합니다.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position] as ProductUiModel)
            is RecentlyViewedViewHolder -> Unit
            is ShowMoreViewHolder -> Unit
        }
    }

    fun initProducts(products: List<ProductUiModel>) {
        val preSize = items.size
        items.addAll(products)
        notifyItemRangeInserted(preSize, items.size - 1)
    }

    fun initRecentlyViewedProducts(products: List<RecentlyViewedProduct>) {
        if (items.isNotEmpty() and (items[0] is RecentlyViewed)) {
            items.removeFirst()
            notifyItemRemoved(0)
        }
        items.add(0, RecentlyViewed(recentlyViewedProducts = products))
        notifyItemInserted(0)
        recentlyViewedAdapter.submitList((items.first() as RecentlyViewed).recentlyViewedProducts)
    }

    fun initShowMoreItem() {
        items.add(ShowMoreItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun showMoreProducts() {
        deleteShowMore()
        clickShowMore()
    }

    private fun deleteShowMore() {
        val lastItem = items.last()
        if (lastItem.viewType == SHOW_MORE) {
            items.removeLast()
            notifyItemRemoved(itemCount)
        }
    }
}
