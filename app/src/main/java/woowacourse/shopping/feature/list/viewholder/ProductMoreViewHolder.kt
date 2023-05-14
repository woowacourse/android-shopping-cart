package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.feature.list.item.ProductView

class ProductMoreViewHolder(
    parent: ViewGroup,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_more, parent, false),
) {
    private val binding = ItemMoreBinding.bind(itemView)

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        binding.moreButton.setOnClickListener { onClick(productView) }
    }
}
