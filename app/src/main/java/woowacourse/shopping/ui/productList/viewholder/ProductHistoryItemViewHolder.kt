package woowacourse.shopping.ui.productList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductHistoryBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.OnProductNavigator

class ProductHistoryItemViewHolder(
    private val binding: HolderProductHistoryBinding,
    private val navigator: OnProductNavigator,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.productNavigator = navigator
    }
}
