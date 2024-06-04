package woowacourse.shopping.presentation.ui.shopping.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    private val shoppingHandler: ShoppingHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecentProductItem) {
        binding.recentProduct = item
        binding.shoppingHandler = shoppingHandler
    }
}
