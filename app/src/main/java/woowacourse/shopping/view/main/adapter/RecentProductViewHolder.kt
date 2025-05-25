package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.main.event.ProductsAdapterEventHandler
import woowacourse.shopping.view.util.QuantitySelectorEventHandler

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecentProduct) {
        binding.product = item.product
    }
}