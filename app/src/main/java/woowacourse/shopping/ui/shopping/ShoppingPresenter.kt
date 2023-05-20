package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiProductCount
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View

class
ShoppingPresenter(
    view: View,
    private val recentProductRepository: RecentProductRepository,
    private val basketRepository: BasketRepository,
    private val recentProductSize: Int = 10,
    productLoadSizeAtOnce: Int = 20,
) : Presenter(view) {
    private var currentPage: PageNumber =
        PageNumber(sizePerPage = productLoadSizeAtOnce)
    private var recentProducts = RecentProducts()
    private var basket = Basket(loadUnit = productLoadSizeAtOnce)
    private val basketProductCount: UiProductCount
        get() = UiProductCount(basketRepository.getProductInBasketSize())

    override fun fetchAll() {
        fetchProducts()
        fetchRecentProducts()
    }

    override fun fetchProducts() {
        updateBasket(basketRepository.getProductInRange(currentPage.getStartPage(), currentPage))
        view.updateLoadMoreVisible()
    }

    override fun fetchRecentProducts() {
        updateRecentProducts(recentProductRepository.getPartially(recentProductSize))
    }

    override fun loadMoreProducts() {
        currentPage = currentPage.next()
        updateBasket(basket + basketRepository.getProductByPage(currentPage))
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

    override fun navigateToBasket() {
        view.navigateToBasket()
    }

    override fun increaseCartCount(product: UiProduct, count: Int) {
        val newProduct = product.toDomain()
        basketRepository.increaseCartCount(newProduct, count)
        updateBasket(basket.increaseProductCount(newProduct, count))
    }

    override fun decreaseCartCount(product: UiProduct, count: Int) {
        val removingProduct = product.toDomain()
        basketRepository.decreaseCartCount(removingProduct, count)
        updateBasket(basket.decreaseProductCount(removingProduct, count))
    }

    private fun View.updateLoadMoreVisible() {
        if (basket.canLoadMore(currentPage)) showLoadMoreButton() else hideLoadMoreButton()
    }

    private fun updateBasket(newBasket: Basket) {
        basket = basket.update(newBasket)
        updateBasketView()
    }

    private fun updateBasketView() {
        view.updateBasketProductBadge(basketProductCount)
        view.updateProducts(basket.takeItemsUpTo(currentPage).toUi())
    }

    private fun updateRecentProducts(newRecentProducts: RecentProducts) {
        recentProducts = recentProducts.update(newRecentProducts)
        view.updateRecentProducts(recentProducts.getItems().toUi())
    }
}
