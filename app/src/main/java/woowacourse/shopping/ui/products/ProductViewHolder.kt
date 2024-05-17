package woowacourse.shopping.ui.products

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val itemClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.itemLayout.setOnClickListener {
            itemClickListener(product.id)
        }
    }
}
