package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

class RecentProductViewHolder(
    parent: ViewGroup,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recent, parent, false),
) {
    private val binding = ItemRecentBinding.bind(itemView)

    override fun bind(productView: ProductView) {
        val productItem = productView as CartProductItem
        binding.product = productItem
    }
}
