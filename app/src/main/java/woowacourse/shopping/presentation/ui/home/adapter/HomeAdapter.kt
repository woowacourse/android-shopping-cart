package woowacourse.shopping.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.presentation.model.HomeData
import woowacourse.shopping.presentation.model.ProductItem
import woowacourse.shopping.presentation.model.RecentlyViewedItem
import woowacourse.shopping.presentation.model.ShowMoreItem
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.PRODUCT
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.RECENTLY_VIEWED
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ProductViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ShowMoreViewHolder

class HomeAdapter(
    private val recentlyViewedAdapter: RecentlyViewedProductAdapter,
    private val productClickListener: ProductClickListener,
    private val clickShowMore: () -> Unit,
    private val quantityChangeListener: (position: Int, op: Operator) -> Unit,
) : ListAdapter<HomeData, RecyclerView.ViewHolder>(HomeComparator()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).viewType) {
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
                    quantityChangeListener,
                )
            }
            RECENTLY_VIEWED.ordinal -> {
                RecentlyViewedViewHolder(
                    RecentlyViewedViewHolder.getView(parent),
                    recentlyViewedAdapter,
                )
            }
            SHOW_MORE.ordinal -> {
                ShowMoreViewHolder(ShowMoreViewHolder.getView(parent)) { clickShowMore() }
            }
            else -> throw IllegalArgumentException("HomeAdapter의 아이템 viewType이 이상합니다.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bind(getItem(position) as ProductItem)
            is RecentlyViewedViewHolder -> Unit
            is ShowMoreViewHolder -> Unit
        }
    }

    /*fun initProducts(products: List<ProductItem>) {
        val preSize = items.size
        items.addAll(products)
        notifyItemRangeInserted(preSize, items.size - 1)
    }

    fun initRecentlyViewedProducts(products: List<RecentlyViewedProduct>) {
        if (items.isNotEmpty() and (items[0] is RecentlyViewedItem)) {
            items.removeFirst()
            notifyItemRemoved(0)
        }
        items.add(0, RecentlyViewedItem(recentlyViewedProducts = products))
        notifyItemInserted(0)
        recentlyViewedAdapter.submitList((items.first() as RecentlyViewedItem).recentlyViewedProducts)
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
    }*/

    class HomeComparator() : DiffUtil.ItemCallback<HomeData>() {
        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem.viewType == newItem.viewType
        }

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            val oldData = when (oldItem) {
                is ProductItem -> oldItem
                is RecentlyViewedItem -> oldItem
                is ShowMoreItem -> oldItem
            }

            val newData = when (newItem) {
                is ProductItem -> newItem
                is RecentlyViewedItem -> newItem
                is ShowMoreItem -> newItem
            }

            return oldData == newData
        }
    }
}
