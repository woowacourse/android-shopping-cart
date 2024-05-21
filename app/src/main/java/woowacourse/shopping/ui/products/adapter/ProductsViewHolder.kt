package woowacourse.shopping.ui.products.adapter

import android.view.View
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
        if (product.quantity.isMin()) {
            binding.btnAddCartQuantity.root.visibility = View.GONE
            binding.btnProductsAddCart.visibility = View.VISIBLE
        } else {
            binding.btnAddCartQuantity.root.visibility = View.VISIBLE
            binding.btnProductsAddCart.visibility = View.GONE
        }
    }
}

typealias OnClickProductItem = (productId: Long) -> Unit
