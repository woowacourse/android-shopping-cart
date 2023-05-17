package woowacourse.shopping.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.model.RecentProductState

class RecentProductItemViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    val binding = binding as ItemRecentBinding

    fun bind(recentProductState: RecentProductState) {
        binding.recentProduct = recentProductState
    }
}
