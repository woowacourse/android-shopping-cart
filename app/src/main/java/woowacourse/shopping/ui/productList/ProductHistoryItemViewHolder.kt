package woowacourse.shopping.ui.productList

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductHistoryBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.OnProductItemClickListener

class ProductHistoryItemViewHolder(
    private val binding: HolderProductHistoryBinding,
    private val onProductItemClickListener: OnProductItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.onProductItemClickListener = onProductItemClickListener
    }
}
