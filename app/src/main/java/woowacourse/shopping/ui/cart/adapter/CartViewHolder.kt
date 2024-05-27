package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val viewModel: CartViewModel,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.vm = viewModel
    }
}
