package woowacourse.shopping.presentation.productlist.product

import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    showProductDetail: (ProductModel) -> Unit,
) : ProductBaseViewHolder(binding.root, showProductDetail) {

    override fun bind(productModel: ProductModel) {
        _productModel = productModel
        binding.textProductListName.text = productModel.name
        binding.textProductListPrice.text =
            binding.textProductListPrice.context.getString(
                R.string.price_format,
                productModel.price,
            )
        setImage(productModel, binding.imageProductListPoster)
    }
}
