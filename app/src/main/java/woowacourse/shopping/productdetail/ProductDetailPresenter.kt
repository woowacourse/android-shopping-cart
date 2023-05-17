package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.Product

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: Product,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {
    init {
        view.updateProductDetail(product.toViewModel())
    }

    override fun addToCart() {
        cartRepository.insertCartProduct(product)
        view.showCart()
    }
}
