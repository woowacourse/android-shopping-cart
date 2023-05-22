package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.Products
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import woowacourse.shopping.util.SafeLiveData
import woowacourse.shopping.util.SafeMutableLiveData

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
    initProducts: Products = Products(listOf()),
    initCartProducts: CartProductInfoList = CartProductInfoList(listOf()),
) : ProductListContract.Presenter {

    private var products = initProducts

    private val _cartProductInfoList = SafeMutableLiveData(initCartProducts)
    override val cartProductInfoList: SafeLiveData<CartProductInfoList> get() = _cartProductInfoList
    override fun updateProductItems() {
        val receivedProducts =
            productRepository.getProductsWithRange(products.size, PRODUCTS_SIZE)
        products = products.addProducts(receivedProducts)
        view.loadProductModels(products.toPresentation())
    }

    override fun updateRecentProductItems() {
        val recentProducts = getRecentProducts()
        view.loadRecentProductModels(recentProducts.toPresentation())
    }

    override fun putProductInCart(productModel: ProductModel) {
        cartRepository.putProductInCart(productModel.id)
    }

    override fun updateCartProductCount(productModel: ProductModel, count: Int) {
        if (count == 0) cartRepository.deleteCartProductId(productModel.id)
        cartRepository.updateCartProductCount(productModel.id, count)
    }

    override fun updateCartProductInfoList() {
        val cartProductInfoList = cartRepository.getAllCartProductsInfo()
        _cartProductInfoList.value = cartProductInfoList
    }

    private fun getRecentProducts(): Products {
        return Products(recentProductRepository.getRecentProducts(RECENT_PRODUCTS_SIZE))
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }

    companion object {
        private const val PRODUCTS_SIZE = 20
        private const val RECENT_PRODUCTS_SIZE = 10
    }
}
