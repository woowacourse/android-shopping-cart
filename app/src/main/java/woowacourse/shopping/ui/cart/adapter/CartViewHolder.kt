package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.products.ProductUiModel
import woowacourse.shopping.ui.utils.AddCartQuantityBundle
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class CartViewHolder(private val binding: ItemCartBinding) : ViewHolder(binding.root) {
    fun bind(
        productUiModel: ProductUiModel,
        onClickExit: OnClickExit,
        onIncreaseProductQuantity: OnIncreaseProductQuantity,
        onDecreaseProductQuantity: OnDecreaseProductQuantity,
    ) {
        binding.productUiModel = productUiModel
        binding.ivCartExit.setOnClickListener {
            onClickExit(productUiModel.productId)
        }
        binding.addCartQuantityBundle =
            AddCartQuantityBundle(
                productUiModel.productId,
                productUiModel.quantity,
                onIncreaseProductQuantity,
                onDecreaseProductQuantity,
            )
    }
}

typealias OnClickExit = (productId: Long) -> Unit
