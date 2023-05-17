package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import java.time.LocalDateTime

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    productModel: ProductModel,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {
    private val product: Product

    init {
        product = productModel.toDomain()
        view.updateProductDetail(product.toView())
    }

    override fun addToCart() {
        val cartProduct = CartProduct(LocalDateTime.now(), amount = 1, isChecked = true, product)
        cartRepository.addCartProduct(cartProduct)
        view.showCart()
    }
}
