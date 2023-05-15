package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductViewHolder(
    private val binding: ItemProductListBinding,
    onItemViewClick: (ProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onItemViewClick(
                binding.product ?: return@setOnClickListener
            )
        }
    }

    fun bind(product: ProductModel) {
        binding.product = product
    }
}
