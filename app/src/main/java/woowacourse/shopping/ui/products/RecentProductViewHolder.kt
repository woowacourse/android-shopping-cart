package woowacourse.shopping.ui.products

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    private val lifecycleOwner: LifecycleOwner,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        viewModel: ProductContentsViewModel,
    )  {
        binding.product = product
        binding.viewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
    }
}
