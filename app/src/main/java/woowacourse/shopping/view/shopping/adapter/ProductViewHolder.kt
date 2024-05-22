package woowacourse.shopping.view.shopping.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.shopping.ShoppingClickListener

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        clickListener: ShoppingClickListener,
    ) {
        binding.product = product
        binding.clickListener = clickListener
    }
}
