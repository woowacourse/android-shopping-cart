package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.products.ProductUiModel
import woowacourse.shopping.ui.utils.AddCartQuantityBundle
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class ProductsViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        productUiModel: ProductUiModel,
        onClickProductItem: OnClickProductItem,
        onIncreaseProductQuantity: OnIncreaseProductQuantity,
        onDecreaseProductQuantity: OnDecreaseProductQuantity,
    ) {
        binding.productUiModel = productUiModel
        binding.addCartQuantityBundle =
            AddCartQuantityBundle(
                productUiModel.productId,
                productUiModel.quantity,
                onIncreaseProductQuantity,
                onDecreaseProductQuantity,
            )
        binding.ivProduct.setOnClickListener {
            onClickProductItem(productUiModel.productId)
        }
    }
}

typealias OnClickProductItem = (productId: Long) -> Unit
