package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.ProductData
import woowacourse.shopping.databinding.HolderCartBinding

class ShoppingCartItemViewHolder(
    private val binding: HolderCartBinding,
    private val onProductItemClickListener: CartItemRecyclerViewAdapter.OnProductItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductData) {
        binding.product = product
        binding.onProductItemClickListener = onProductItemClickListener
    }
}
