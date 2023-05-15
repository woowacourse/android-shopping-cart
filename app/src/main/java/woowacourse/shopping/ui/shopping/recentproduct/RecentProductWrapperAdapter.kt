package woowacourse.shopping.ui.shopping.recentproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.ShoppingViewType

class RecentProductWrapperAdapter(
    private val recentProductAdapter: RecentProductAdapter
) :
    RecyclerView.Adapter<RecentProductWrapperViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder = RecentProductWrapperViewHolder(parent, recentProductAdapter)

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {}

    override fun getItemCount(): Int = if (recentProductAdapter.currentList.size != 0) 1 else 0

    override fun getItemViewType(position: Int): Int = ShoppingViewType.RECENT_PRODUCTS.value
}
