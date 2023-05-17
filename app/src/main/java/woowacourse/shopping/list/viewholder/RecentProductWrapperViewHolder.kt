package woowacourse.shopping.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.list.adapter.RecentProductListAdapter

class RecentProductWrapperViewHolder(
    private val binding: ViewBinding,
    private val adapter: RecentProductListAdapter
) : ItemHolder(binding) {

    override fun bind() {
        binding as ItemRecentProductListBinding
        binding.recentListRv.adapter = adapter
    }
}
