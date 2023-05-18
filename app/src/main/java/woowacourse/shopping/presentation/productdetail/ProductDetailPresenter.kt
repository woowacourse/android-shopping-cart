package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toPresentation

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ProductDetailContract.Presenter {

    private lateinit var currentProduct: Product

    override fun loadProductDetail(productId: Int) {
        val product = productRepository.findProductById(productId) ?: Product.defaultProduct
        currentProduct = product
        view.showProductDetail(currentProduct.toPresentation())
    }

    override fun putProductInCart() {
        cartRepository.insertCartProduct(currentProduct.id, 1)
        view.showCompleteMessage(currentProduct.name)
    }
}
