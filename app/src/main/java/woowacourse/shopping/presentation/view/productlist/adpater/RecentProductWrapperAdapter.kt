package woowacourse.shopping.presentation.view.productlist.adpater

import android.os.Bundle
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

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind(lastScroll)
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = ViewType.RECENT_PRODUCT_LIST.ordinal

    fun onSaveScrollState(outState: Bundle) {
        outState.putInt(KEY_STATE_LAST_SCROLL, lastScroll)
    }

    fun onRestoreScrollState(savedInstanceState: Bundle) {
        lastScroll = savedInstanceState.getInt(KEY_STATE_LAST_SCROLL)
    }

    companion object {
        internal const val KEY_STATE_LAST_SCROLL = "KEY_STATE_LAST_SCROLL"
    }
}
