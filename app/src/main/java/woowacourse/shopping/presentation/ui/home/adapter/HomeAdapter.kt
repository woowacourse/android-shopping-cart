package woowacourse.shopping.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.HomeViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedViewHolder

class HomeAdapter(
    private val clickProduct: (productId: Long) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val recentlyViewed = mutableListOf<Product>()
    private val items = mutableListOf<Product>()
    val productsCount: Int get() = items.size

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && recentlyViewed.isNotEmpty()) {
            return HomeViewType.RECENTLY_VIEWED.ordinal
        }
        return HomeViewType.PRODUCT.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HomeViewType.RECENTLY_VIEWED.ordinal) {
            return RecentlyViewedViewHolder(RecentlyViewedViewHolder.getView(parent), clickProduct)
        }
        return HomeViewHolder(HomeViewHolder.getView(parent)) { clickProduct(items[it].id) }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(items[position])
            is RecentlyViewedViewHolder -> holder.bind(recentlyViewed)
        }
    }

    override fun getItemCount(): Int {
        // if (recentlyViewed.isEmpty()) return items.size
        return items.size
    }

    fun initProducts(products: List<Product>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
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
