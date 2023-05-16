package woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductWrapperBinding

class RecentProductWrapperViewHolder(
    parent: ViewGroup,
    recentProductAdapter: RecentProductAdapter,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recent_product_wrapper, parent, false)
) {
    init {
        val binding = ItemRecentProductWrapperBinding.bind(itemView)
        binding.adapter = recentProductAdapter
    }
}
