package woowacourse.shopping.presentation.productlist.recentproduct

import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productlist.product.ProductBaseViewHolder

class RecentProductItemViewHolder(
    private val binding: ItemRecentProductBinding,
    showProductDetail: (ProductModel) -> Unit,
) : ProductBaseViewHolder(binding.root, showProductDetail) {

    override fun bind(recentProduct: ProductModel) {
        _productModel = recentProduct
        binding.textProductListName.text = recentProduct.name
        setImage(recentProduct, binding.imageProductListPoster)
    }
}
