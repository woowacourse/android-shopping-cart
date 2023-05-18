package woowacourse.shopping.feature.product.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.ViewType

class RecentProductListWrapperAdapter(
    private val recentProductListAdapter: RecentProductListAdapter,
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder.createInstance(parent, recentProductListAdapter)
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = ViewType.RECENT_PRODUCT_LIST.ordinal
}
