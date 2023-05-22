package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.DomainCartProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductCount
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.RecentProducts
import woowacourse.shopping.domain.model.page.LoadMore
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiProductCount
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View

class ShoppingPresenter(
    view: View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductSize: Int = 10,
    productLoadSizeAtOnce: Int = 20,
) : Presenter(view) {
    private var currentPage: Page = LoadMore(sizePerPage = productLoadSizeAtOnce)
    private var recentProducts = RecentProducts()
    private var cart = Cart(loadUnit = productLoadSizeAtOnce)
    private val cartProductCount: UiProductCount
        get() = UiProductCount(cartRepository.getProductInCartSize())

    override fun fetchAll() {
        fetchProducts()
        fetchRecentProducts()
    }

    override fun fetchProducts() {
        updateCart(cart + loadCartProducts(currentPage))
        view.updateLoadMoreVisible()
    }

    private fun convertToCartProduct(product: Product): DomainCartProduct {
        val cartEntity = cartRepository.getCartEntity(product.id)
        return DomainCartProduct(
            cartEntity.id,
            product,
            ProductCount(cartEntity.count),
            cartEntity.checked
        )
    }

    override fun fetchRecentProducts() {
        updateRecentProducts(recentProductRepository.getPartially(recentProductSize))
    }

    override fun loadMoreProducts() {
        currentPage = currentPage.next()
        updateCart(cart + loadCartProducts(currentPage))
        view.updateLoadMoreVisible()
    }

    override fun inquiryProductDetail(product: UiProduct) {
        val recentProduct = RecentProduct(product = product.toDomain())
        view.navigateToProductDetail(product, recentProducts.getLatest()?.toUi())
        recentProductRepository.add(recentProduct)
        updateRecentProducts(recentProducts + recentProduct)
    }

    override fun inquiryRecentProductDetail(recentProduct: UiRecentProduct) {
        view.navigateToProductDetail(recentProduct.product, recentProducts.getLatest()?.toUi())
        recentProductRepository.add(recentProduct.toDomain())
    }

    override fun navigateToCart() {
        view.navigateToCart()
    }

    override fun increaseCartCount(product: UiProduct, count: Int) {
        val newProduct = product.toDomain()
        cartRepository.increaseCartCount(newProduct, count)
        updateCart(cart.increaseProductCount(newProduct, count))
    }

    override fun decreaseCartCount(product: UiProduct, count: Int) {
        val removingProduct = product.toDomain()
        cartRepository.decreaseCartCount(removingProduct, count)
        updateCart(cart.decreaseProductCount(removingProduct, count))
    }

    private fun View.updateLoadMoreVisible() {
        if (currentPage.hasNext(cart)) showLoadMoreButton() else hideLoadMoreButton()
    }

    private fun updateCart(newCart: Cart) {
        cart = cart.update(newCart)
        updateCartView()
    }

    private fun updateCartView() {
        view.updateCartBadge(cartProductCount)
        view.updateProducts(currentPage.takeItems(cart).toUi())
    }

    private fun updateRecentProducts(newRecentProducts: RecentProducts) {
        recentProducts = recentProducts.update(newRecentProducts)
        view.updateRecentProducts(recentProducts.getItems().toUi())
    }

    private fun loadCartProducts(page: Page): List<DomainCartProduct> = productRepository
        .getProductByPage(page)
        .map { convertToCartProduct(it) }
}
