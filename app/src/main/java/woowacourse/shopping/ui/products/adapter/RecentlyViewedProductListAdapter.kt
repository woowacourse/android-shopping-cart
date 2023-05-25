package woowacourse.shopping.ui.products.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class RecentlyViewedProductListAdapter(
    private val recentlyViewedProducts: List<RecentlyViewedProductUIState>,
    private val onClick: (Long) -> Unit,
) : RecyclerView.Adapter<RecentlyViewedProductListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedProductListViewHolder {
        return RecentlyViewedProductListViewHolder.create(parent, onClick)
    }

    override fun getItemCount(): Int = recentlyViewedProducts.size

    override fun onBindViewHolder(holder: RecentlyViewedProductListViewHolder, position: Int) {
        holder.bind(recentlyViewedProducts[position])
    }


}
