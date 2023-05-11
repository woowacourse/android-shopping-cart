package woowacourse.shopping.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.PRODUCT
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.RECENTLY_VIEWED
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.HomeViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ShowMoreViewHolder

class HomeAdapter(
    private val clickProduct: (productId: Long) -> Unit,
    private val clickMore: (productId: Long) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val recentlyViewed = mutableListOf<Product>()
    private val items = mutableListOf<Product>()
    val productsCount: Int get() = items.size

    override fun getItemViewType(position: Int): Int {
        if (items[position].id == -1L) return SHOW_MORE.ordinal
        if (position == 0 && recentlyViewed.isNotEmpty()) return RECENTLY_VIEWED.ordinal
        return PRODUCT.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (HomeViewType.valueOf(viewType)) {
            RECENTLY_VIEWED -> RecentlyViewedViewHolder(
                RecentlyViewedViewHolder.getView(parent),
                clickProduct,
            )
            PRODUCT -> HomeViewHolder(HomeViewHolder.getView(parent)) { clickProduct(items[it].id) }
            SHOW_MORE -> ShowMoreViewHolder(
                ShowMoreViewHolder.getView(parent),
            ) {
                hideShowMoreButton()
                clickMore(items.last().id)
            }
        }
    }

    private fun hideShowMoreButton() {
        items.removeLast()
        notifyItemRemoved(itemCount - 1)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(items[position])
            is RecentlyViewedViewHolder -> holder.bind(recentlyViewed)
            is ShowMoreViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        // if (recentlyViewed.isEmpty()) return items.size
        return items.size
    }

    fun initProducts(products: List<Product>) {
        val preSize = items.size
        items.addAll(products)
        notifyItemRangeInserted(preSize, items.size - 1)
    }

    fun initShowMoreButton() {
        items.add(Product(-1, "", "", -1))
        notifyItemInserted(productsCount - 1)
    }

    fun initRecentlyViewedProduct(recentlyViewedProducts: List<Product>) {
        if (recentlyViewed.isEmpty()) {
            recentlyViewed.clear()
            recentlyViewed.addAll(recentlyViewedProducts)
            notifyItemInserted(0)
            return
        }
        recentlyViewed.clear()
        recentlyViewed.addAll(recentlyViewedProducts)
        notifyItemChanged(0)
    }
}
