package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductViewHolder(
    private val binding: ItemProductListBinding,
    onItemViewClick: (CartProductModel) -> Unit,
    onMinusClick: (CartProductModel) -> Unit,
    onPlusClick: (CartProductModel) -> Unit,
    onCartAddClick: (CartProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onItemViewClick(binding.cartProduct ?: return@setOnClickListener)
        }
        binding.productListCartAddButton.setOnClickListener {
            onCartAddClick(binding.cartProduct ?: return@setOnClickListener)
        }
        binding.onMinusClick = onMinusClick
        binding.onPlusClick = onPlusClick
    }

    fun bind(cartProduct: CartProductModel) {
        binding.cartProduct = cartProduct
    }
}
