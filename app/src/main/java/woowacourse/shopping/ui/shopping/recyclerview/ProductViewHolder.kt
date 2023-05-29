package woowacourse.shopping.ui.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.ui.model.ShoppingProductModel

class ProductViewHolder(
    private val binding: ItemProductListBinding,
    onItemViewClick: (Int) -> Unit,
    onMinusAmountButtonViewClick: (Int) -> Unit,
    onPlusAmountButtonViewClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { onItemViewClick(bindingAdapterPosition) }
        binding.cartProductAmountMinusButton.setOnClickListener { onMinusAmountButtonViewClick(bindingAdapterPosition) }
        binding.newCartProductAmountPlusButton.setOnClickListener { onPlusAmountButtonViewClick(bindingAdapterPosition) }
        binding.cartProductAmountPlusButton.setOnClickListener { onPlusAmountButtonViewClick(bindingAdapterPosition) }
    }

    fun bind(product: ShoppingProductModel) {
        binding.shoppingProduct = product
    }
}
