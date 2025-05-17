package woowacourse.shopping.view.products

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.products.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productClickListener: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.product = item
        binding.btnSelectedProduct.setOnClickListener {
            productClickListener(item)
        }
    }
}
