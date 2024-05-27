package woowacourse.shopping.presentation.ui.shopping.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

class RecentViewHolder(
    private val binding: ItemRecentBinding,
    private val shoppingHandler: ShoppingHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecentProduct) {
        binding.recentProduct = item
        binding.shoppingHandler = shoppingHandler
    }
}
