package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.Product
import woowacourse.shopping.databinding.HolderCartBinding

class ShoppingCartItemViewHolder(
    private val binding: HolderCartBinding,
    private val onCartItemListener: OnCartItemListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.onCartItemListener = onCartItemListener
    }
}
