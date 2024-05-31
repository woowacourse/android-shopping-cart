package woowacourse.shopping.presentation.ui.shopping.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.presentation.ui.CartQuantityActionHandler
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

class ShoppingViewHolder(
    private val binding: ItemProductBinding,
    private val shoppingActionHandler: ShoppingActionHandler,
    private val cartQuantityActionHandler: CartQuantityActionHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.shoppingActionHandler = shoppingActionHandler
        binding.cartQuantityActionHandler = cartQuantityActionHandler
        binding.executePendingBindings()
    }

    fun bindPartial(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.shoppingActionHandler = shoppingActionHandler
        binding.cartQuantityActionHandler = cartQuantityActionHandler
        binding.executePendingBindings()
    }
}
