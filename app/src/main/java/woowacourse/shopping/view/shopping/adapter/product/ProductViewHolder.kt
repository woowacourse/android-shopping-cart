package woowacourse.shopping.view.shopping.adapter.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.cart.QuantityClickListener
import woowacourse.shopping.view.shopping.ShoppingClickListener

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        productItem: ShoppingItem.ProductItem,
        shoppingClickListener: ShoppingClickListener,
        quantityClickListener: QuantityClickListener,
    ) {
        binding.productItem = productItem
        binding.shoppingClickListener = shoppingClickListener
        binding.quantityClickListener = quantityClickListener
    }
}
