package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ItemState
import woowacourse.shopping.feature.list.item.ProductItem
import java.text.DecimalFormat

class ProductViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    val binding = binding as ItemProductBinding

    override fun bind(itemState: ItemState) {
        val productItem = itemState as ProductItem

        binding.productName.text = productItem.name
        binding.productPrice.text = DecimalFormat("#,###").format(productItem.price)
    }
}
