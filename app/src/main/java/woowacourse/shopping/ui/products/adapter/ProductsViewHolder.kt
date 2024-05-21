package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.utils.AddCartQuantityBundle
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class ProductsViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        onClickProductItem: OnClickProductItem,
        onIncreaseProductQuantity: OnIncreaseProductQuantity,
        onDecreaseProductQuantity: OnDecreaseProductQuantity,
    ) {
        binding.product = product
        binding.addCartQuantityBundle = AddCartQuantityBundle(product, onIncreaseProductQuantity, onDecreaseProductQuantity)
        binding.ivProduct.setOnClickListener {
            onClickProductItem(product.id)
        }
    }
}

typealias OnClickProductItem = (productId: Long) -> Unit
