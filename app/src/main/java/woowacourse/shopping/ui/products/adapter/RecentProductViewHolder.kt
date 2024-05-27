package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    private val viewModel: ProductContentsViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.vm = viewModel
    }
}
