package woowacourse.shopping.presentation.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartItemViewHolder(
    private val binding: ItemCartBinding,
    deleteItem: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var _productModel: ProductModel? = null
    private val productModel get() = _productModel!!

    init {
        binding.imageCartDelete.setOnClickListener { deleteItem(productModel) }
    }

    fun bind(product: ProductModel) {
        _productModel = product
        binding.productModel = productModel
    }
}
