package woowacourse.shopping.presentation.ui.shopping.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingEventHandler
import woowacourse.shopping.presentation.ui.shopping.ShoppingItemCountHandler

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
