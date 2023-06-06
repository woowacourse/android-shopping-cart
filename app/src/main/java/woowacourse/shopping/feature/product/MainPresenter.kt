package woowacourse.shopping.feature.product

import com.example.domain.Cart
import com.example.domain.CartProduct
import com.example.domain.Product
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.feature.cart.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.feature.product.model.ProductState
import woowacourse.shopping.feature.product.model.toDomain
import woowacourse.shopping.feature.product.model.toUi
import woowacourse.shopping.feature.product.recent.model.RecentProductState
import woowacourse.shopping.feature.product.recent.model.toUi
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val cart: Cart = Cart(),
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository
) : MainContract.Presenter {

    private val loadItemCountUnit = 20
    private var loadItemFromIndex = 0

    override fun loadCart() {
        cart.updateAll(cartRepository.getAll())
    }

    override fun loadMoreProducts() {
        productRepository.fetchNextProducts(
            lastProductId = loadItemFromIndex.toLong(),
            onFailure = {
                view.showEmptyProducts()
                view.setProducts(listOf())
            },
            onSuccess = { products ->
                view.addProductItems(products.map(Product::toUi))
            }
        )
        loadItemFromIndex += loadItemCountUnit
    }

    override fun loadRecentProducts() {
        view.setRecentProducts(recentProductRepository.getAll())
    }

    override fun loadCartSize() {
        val cartProductCount = cart.products.size
        if (cartProductCount >= MIN_COUNT_VALUE) {
            view.setCartSize(cartProductCount)
            view.showCartSizeBadge()
        } else view.hideCartSizeBadge()
    }

    override fun addRecentProduct(product: Product) {
        val nowDateTime: LocalDateTime = LocalDateTime.now()
        storeRecentProduct(product, nowDateTime)
        view.setRecentProducts(recentProductRepository.getAll())
    }

    override fun showProductDetail(productState: ProductState) {
        val recentProduct: RecentProductState? =
            recentProductRepository.getMostRecentProduct()?.toUi()
        view.showProductDetail(productState, recentProduct)
        addRecentProduct(productState.toDomain())
    }

    override fun storeCartProduct(productState: ProductState) {
        cartRepository.addProduct(productState.id, MIN_COUNT_VALUE)
        cart.addProduct(productState.toDomain())
        loadCartSize()
    }

    override fun minusCartProductCount(productState: ProductState) {
        val cartProduct: CartProduct = cart.getByProductId(productState.id) ?: return
        val cartProductCount: Int = cartProduct.count - 1
        cartRepository.updateCartProductCount(productState.id, cartProductCount)
        loadCartSize()
    }

    override fun plusCartProductCount(productState: ProductState) {
        val cartProduct: CartProduct = cart.getByProductId(productState.id) ?: return
        val cartProductCount: Int = cartProduct.count + 1
        cartRepository.updateCartProductCount(productState.id, cartProductCount)
    }

    private fun storeRecentProduct(product: Product, viewedDateTime: LocalDateTime) {
        recentProductRepository.addRecentProduct(product, viewedDateTime)
    }
}
