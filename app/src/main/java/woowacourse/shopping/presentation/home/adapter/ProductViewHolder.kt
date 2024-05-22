package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.cart.Order

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productItemClickListener: ProductItemClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productItemClickListener = productItemClickListener
    }

    fun bind(order: Order) {
        binding.order = order
    }
}
