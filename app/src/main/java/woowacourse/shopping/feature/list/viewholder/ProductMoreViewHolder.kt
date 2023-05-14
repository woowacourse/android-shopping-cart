package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.feature.list.item.ProductView

class ProductMoreViewHolder(binding: ViewBinding) : ItemViewHolder(binding) {
    private val binding = binding as ItemMoreBinding

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        binding.moreButton.setOnClickListener { onClick(productView) }
    }
}
