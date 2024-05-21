package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        clickListener: ShoppingClickListener.ShoppingItemClickListener,
    ) {
        binding.product = product
        binding.clickListener = clickListener
    }
}
