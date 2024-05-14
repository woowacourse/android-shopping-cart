package woowacourse.shopping.feature.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Product

class CartViewHolder(private val binding: ItemCartBinding) : ViewHolder(binding.root) {
    fun bind(
        onClickExit: OnClickExit,
        product: Product,
    ) {
        binding.product = product
        binding.ivCartExit.setOnClickListener {
            onClickExit(product.id)
        }
    }
}

typealias OnClickExit = (productId: Long) -> Unit
