package woowacourse.shopping.ui.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ListRecentProductLayoutBinding

class RecentProductWrapperAdapter(private val recentProductAdapter: RecentProductAdapter) :
    RecyclerView.Adapter<RecentProductWrapperViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder(
            ListRecentProductLayoutBinding.inflate(LayoutInflater.from(parent.context)),
            recentProductAdapter.itemCount
        )
    }

    override fun getItemCount(): Int {
        return if (recentProductAdapter.itemCount == 0) 0
        else 1
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind(recentProductAdapter)
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    fun updateRecentProduct() {
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}
