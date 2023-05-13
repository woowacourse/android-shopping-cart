package woowacourse.shopping.view.shoppingmain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentProductWrapperAdapter(
    private val recentProductAdapter: RecentProductAdapter
) : RecyclerView.Adapter<RecentProductWrapperViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    override fun getItemCount(): Int {
        if (recentProductAdapter.itemCount == 0) {
            return 0
        }
        return 1
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind(recentProductAdapter)
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}
