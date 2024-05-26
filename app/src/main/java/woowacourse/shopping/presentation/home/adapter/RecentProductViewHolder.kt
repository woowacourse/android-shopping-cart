package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
    }
}
