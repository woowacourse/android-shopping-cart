package woowacourse.shopping.ui.cart

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Product

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val lifecycleOwner: LifecycleOwner,
    private val onRemoveButtonClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        viewModel: CartViewModel,
    ) {
        binding.product = product
        binding.viewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        binding.ivRemove.setOnClickListener {
            onRemoveButtonClick(product.id)
        }
    }
}
