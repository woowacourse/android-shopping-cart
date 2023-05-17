package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.view.productlist.viewholder.RecentProductWrapperViewHolder

class RecentProductWrapperAdapter(
    private val onRestoreLastScroll: () -> Int,
    private val onSavedLastScroll: (Int) -> Unit,
    private val recentAdapter: RecentProductListAdapter,
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder(parent, recentAdapter) { x ->
            onSavedLastScroll(x)
        }
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind(onRestoreLastScroll())
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = ViewType.RECENT_PRODUCT_LIST.ordinal
}
