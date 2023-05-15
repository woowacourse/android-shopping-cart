package woowacourse.shopping.view.shoppingmain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentProductWrapperAdapter(
    private val recentProductAdapter: RecentProductAdapter
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {
    private var recentProductAdapterItemCount = recentProductAdapter.itemCount

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    override fun getItemCount(): Int {
        if (recentProductAdapterItemCount == RECENT_PRODUCTS_VIEW_INVISIBLE_COUNT) {
            return RECENT_PRODUCTS_VIEW_INVISIBLE_COUNT
        }
        return RECENT_PRODUCTS_VIEW_VISIBLE_COUNT
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind(recentProductAdapter)
    }

    fun update(itemCount: Int) {
        recentProductAdapterItemCount = itemCount
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 1
        private const val RECENT_PRODUCTS_VIEW_INVISIBLE_COUNT = 0
        private const val RECENT_PRODUCTS_VIEW_VISIBLE_COUNT = 1
    }
}
