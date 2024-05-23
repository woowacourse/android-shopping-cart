package woowacourse.shopping.view.shopping.adapter.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.shopping.ShoppingClickListener

class RecentViewHolder(private val binding: ItemRecentViewedProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        clickListener: ShoppingClickListener,
    ) {
        binding.product = product
        binding.clickListener = clickListener
    }
}
