package woowacourse.shopping.view.home.adapter.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.cart.QuantityClickListener
import woowacourse.shopping.view.home.HomeClickListener

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        productItem: ShoppingItem.ProductItem,
        homeClickListener: HomeClickListener,
        quantityClickListener: QuantityClickListener,
    ) {
        binding.productItem = productItem
        binding.shoppingClickListener = homeClickListener
        binding.quantityClickListener = quantityClickListener
    }
}
