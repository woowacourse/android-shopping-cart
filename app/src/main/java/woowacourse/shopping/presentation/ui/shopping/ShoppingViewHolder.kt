package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingProduct

class ShoppingViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        shoppingProduct: ShoppingProduct,
        eventHandler: ShoppingEventHandler,
        shoppingItemCountHandler: ShoppingItemCountHandler,
    ) {
        binding.product = product
        binding.shoppingProduct = shoppingProduct
        binding.eventHandler = eventHandler
        binding.countHandler = shoppingItemCountHandler
    }
}
