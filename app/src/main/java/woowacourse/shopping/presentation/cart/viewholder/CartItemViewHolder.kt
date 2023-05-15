package woowacourse.shopping.presentation.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartItemViewHolder(
    private val binding: ItemCartBinding,
    deleteItem: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var productModel: ProductModel

    init {
        binding.imageCartDelete.setOnClickListener { deleteItem(productModel) }
    }

    fun bind(product: ProductModel) {
        productModel = product
        binding.productModel = product
    }
}
