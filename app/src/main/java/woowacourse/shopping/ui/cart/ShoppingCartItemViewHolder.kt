package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.domain.model.Product

class ShoppingCartItemViewHolder(
    private val binding: HolderCartBinding,
    private val onProductItemClickListener: CartItemRecyclerViewAdapter.OnProductItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
//    fun bind(product: ProductData) {
//        binding.product = product
//        binding.onProductItemClickListener = onProductItemClickListener
//    }

    fun bind(product: Product) {
        binding.product = product
        binding.onProductItemClickListener = onProductItemClickListener
    }
}
