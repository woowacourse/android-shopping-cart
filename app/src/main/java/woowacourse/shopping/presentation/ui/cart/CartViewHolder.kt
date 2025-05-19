package woowacourse.shopping.presentation.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Product

class CartViewHolder(
    private val binding: ItemCartBinding,
    onClickHandler: OnClickHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClickHandler = onClickHandler
    }

    fun bind(product: Product) {
        binding.product = product
    }

    interface OnClickHandler {
        fun onRemoveCartProductClick(id: Int)
    }
}
