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
    recentProductModel: ProductModel?,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {
    private val product: Product
    private val recentProduct: Product?

    init {
        product = productModel.toDomain()
        recentProduct = recentProductModel?.toDomain()
        view.setupProductDetail(product.toView())
        view.setupRecentProductDetail(recentProduct?.toView())
    }

    override fun setupCartProductDialog() {
        view.showCartProductDialog(product.toView())
    }

    override fun addToCart() {
        val cartProduct = CartProduct(LocalDateTime.now(), amount = 1, isChecked = true, product)
        cartRepository.addCartProduct(cartProduct)
        view.showCart()
    }
}
