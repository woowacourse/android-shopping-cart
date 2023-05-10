package woowacourse.shopping.presentation.cart

import woowacourse.shopping.Product
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : CartContract.Presenter {
    override fun initCart() {
        val cartProductModels = getCartProductModels()
        view.initCartProductModels(cartProductModels)
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProductId(productModel.id)
        view.setCartProductModels(getCartProductModels())
    }

    private fun getCartProductModels(): List<ProductModel> {
        val recentProductIds = cartRepository.getCartProductIds()
        return findProductsById(recentProductIds)
    }

    private fun findProductsById(productIds: List<Int>): List<ProductModel> {
        return productIds.map {
            val product = productRepository.findProductById(it) ?: Product.defaultProduct
            product.toPresentation()
        }
    }
}
