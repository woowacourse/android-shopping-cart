package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductModel,
    private val recentProduct: ProductModel?,
    private val recentProductRepository: RecentProductRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {
    init {
        view.initRecentProduct(recentProduct)
        view.updateProductDetail(product)
    }

    override fun showRecentProductDetail() {
        if (recentProduct == null) return

        val products = productRepository.selectAll()
        val recentProducts = recentProductRepository.selectAll(products)
        val madeRecentProduct = recentProducts.makeRecentProduct(product.toDomainModel())

        recentProductRepository.insertRecentProduct(madeRecentProduct)

        view.showProductDetail(recentProduct)
    }

    override fun showCartCounter() {
        view.openCartCounter(CartProductModel(DEFAULT_CART_PRODUCT_AMOUNT, product))
    }

    override fun addToCart(cartProduct: CartProductModel) {
        cartRepository.addCartProduct(cartProduct.toDomainModel())
        view.close()
    }

    companion object {
        private const val DEFAULT_CART_PRODUCT_AMOUNT = 1
    }
}
