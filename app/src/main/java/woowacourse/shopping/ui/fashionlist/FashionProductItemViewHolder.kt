package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.domain.product.CartItem

class FashionProductItemViewHolder(
    private val binding: ProductItemBinding,
    viewModel: ProductListViewModel,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewModel = viewModel
        binding.productClickListener = productClickListener
    }

    fun bind(
        item: ProductListViewType.FashionProductItemType,
        cartItem: CartItem?,
    ) {
        val bindingCartItem =
            cartItem ?: CartItem(
                id = item.product.id,
                product = item.product,
                quantity = item.quantity,
            )
        binding.product = item.product
        binding.cartItem = bindingCartItem
    }
}
