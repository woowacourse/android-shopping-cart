package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.Product
import woowacourse.shopping.Products
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
) : ProductListContract.Presenter {

    private var size = PRODUCTS_SIZE
    override fun refreshProductItems() {
        view.loadProductItems(getCartItems().map { it.toPresentation() })
    }

    override fun loadMoreProductItems() {
        size += PRODUCTS_SIZE
        view.loadProductItems(getCartItems().map { it.toPresentation() })
    }

    private fun getCartItems(): List<CartProductInfo> {
        val receivedProducts = productRepository.getProductsWithRange(0, size)
        return getCartProductsByProducts(receivedProducts)
    }

    private fun getCartProductsByProducts(products: List<Product>): List<CartProductInfo> {
        return products.map {
            cartRepository.getCartProductInfoById(it.id) ?: CartProductInfo(
                it,
                0,
            )
        }
    }

    override fun loadRecentProductItems() {
        val recentProducts = getRecentProducts()
        view.loadRecentProductItems(recentProducts.toPresentation())
    }

    override fun putProductInCart(cartProductModel: CartProductInfoModel) {
        cartRepository.putProductInCart(cartProductModel.productModel.id)
    }

    override fun updateCartProductCount(cartProductModel: CartProductInfoModel, count: Int) {
        if (count == 0) {
            cartRepository.deleteCartProductId(cartProductModel.productModel.id)
        } else {
            cartRepository.updateCartProductCount(
                cartProductModel.productModel.id,
                count,
            )
        }
    }

    override fun updateCartCount() {
        view.showCartCount(
            cartRepository.getAllCartProductsInfo().count,
        )
    }

    private fun getRecentProducts(): Products {
        return Products(recentProductRepository.getRecentProducts(RECENT_PRODUCTS_SIZE))
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }

    private fun CartProductInfoList.toPresentation(): List<CartProductInfoModel> {
        return items.map { it.toPresentation() }
    }

    companion object {
        private const val PRODUCTS_SIZE = 20
        private const val RECENT_PRODUCTS_SIZE = 10
    }
}
