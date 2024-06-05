package woowacourse.shopping.presentation.ui.shopping.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

class RecentViewHolder(
    private val binding: ItemRecentBinding,
    private val shoppingActionHandler: ShoppingActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecentProduct) {
        binding.recentProduct = item
        binding.shoppingHandler = shoppingActionHandler
    }
}
