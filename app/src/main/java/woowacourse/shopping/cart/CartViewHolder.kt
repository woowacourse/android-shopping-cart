package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (CartProductModel) -> Unit,
    onMinusClick: (CartProductModel) -> Unit,
    onPlusClick: (CartProductModel) -> Unit,
    onCheck: (CheckableCartProductModel, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartProductListRemoveButton.setOnClickListener {
            onCartItemRemoveButtonViewClick(
                binding.checkableCartProduct?.cartProduct ?: return@setOnClickListener
            )
        }
        binding.cartProductListCheck.setOnCheckedChangeListener { _, isChecked ->
            onCheck(binding.checkableCartProduct ?: return@setOnCheckedChangeListener, isChecked)
        }
        binding.onMinusClick = onMinusClick
        binding.onPlusClick = onPlusClick
    }

    fun bind(checkableCartProduct: CheckableCartProductModel) {
        binding.checkableCartProduct = checkableCartProduct
    }
}
