package woowacourse.shopping.presentation.productlist.product

import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    showProductDetail: (ProductModel) -> Unit,
) : ProductBaseViewHolder(binding.root, showProductDetail) {

    override fun bind(product: ProductModel) {
        _productModel = product
        binding.productModel = productModel
    }
}
