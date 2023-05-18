package woowacourse.shopping.feature.product.recent

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding

class RecentProductWrapperViewHolder(
    private val binding: ViewBinding,
    private val adapter: RecentProductListAdapter
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding as ItemRecentProductListBinding
        binding.recentListRv.adapter = adapter
    }
}
