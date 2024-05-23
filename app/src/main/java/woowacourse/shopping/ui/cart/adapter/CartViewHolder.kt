package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.CountButtonClickListener

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val itemRemoveClickListener: (Long) -> Unit,
    private val plusCountClickListener: (Long) -> Unit,
    private val minusCountClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.ivRemove.setOnClickListener {
            itemRemoveClickListener(productWithQuantity.product.id)
        }
        binding.countButtonClickListener = countButtonClickListener(productWithQuantity)
    }

    private fun countButtonClickListener(productWithQuantity: ProductWithQuantity) =
        object : CountButtonClickListener {
            override fun plusCount() {
                plusCountClickListener(productWithQuantity.product.id)
            }

            override fun minusCount() {
                minusCountClickListener(productWithQuantity.product.id)
            }
        }
}
