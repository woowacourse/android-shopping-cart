package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.data.repository.CartRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductModel,
    private val recentProduct: ProductModel?,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {
    init {
        view.initRecentProduct(recentProduct)
        view.updateProductDetail(product)
    }

    override fun addToCart() {
        cartRepository.plusCartProduct(product.toDomainModel())
        view.showCart()
    }
}
