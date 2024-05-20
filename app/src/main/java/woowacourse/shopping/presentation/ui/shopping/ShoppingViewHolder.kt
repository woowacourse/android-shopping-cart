package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class ShoppingViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        actionHandler: ShoppingActionHandler,
    ) {
        binding.product = product
        binding.actionHandler = actionHandler
    }
}
