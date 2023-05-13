package woowacourse.shopping.presentation.productlist.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var productModel: ProductModel

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }
    fun bind(product: ProductModel) {
        productModel = product
        binding.productModel = product
    }
}
