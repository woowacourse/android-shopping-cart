package woowacourse.shopping.ui.products

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val lifecycleOwner: LifecycleOwner,
    private val itemClickListener: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        viewModel: ProductContentsViewModel,
    ) {
        binding.product = product
        binding.viewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        binding.itemLayout.setOnClickListener {
            itemClickListener(product.id)
        }
    }
}
