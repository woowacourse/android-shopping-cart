package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.product.Product

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.product = item
    }
}
