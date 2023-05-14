package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.view.productlist.viewholder.RecentProductWrapperViewHolder

class RecentProductWrapperAdapter(
    private val recentAdapter: RecentProductListAdapter,
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {
    private var lastScroll = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder(parent, recentAdapter) { x ->
            lastScroll = x
        }
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = ViewType.RECENT_PRODUCT_LIST.ordinal
}
