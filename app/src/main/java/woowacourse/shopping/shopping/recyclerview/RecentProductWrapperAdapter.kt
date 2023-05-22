package woowacourse.shopping.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductListWrapperBinding

class RecentProductWrapperAdapter(private val recentProductAdapter: RecentProductAdapter) :
    RecyclerView.Adapter<RecentProductWrapperViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductWrapperViewHolder {
        return RecentProductWrapperViewHolder(
            ItemRecentProductListWrapperBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return if (recentProductAdapter.itemCount == 0) 0
        else 1
    }

    override fun onBindViewHolder(holder: RecentProductWrapperViewHolder, position: Int) {
        holder.bind(recentProductAdapter)
    }

    fun updateRecentProduct() {
        notifyDataSetChanged()
    }
}
