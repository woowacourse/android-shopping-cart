package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.domain.repository.DomainProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View

class ShoppingPresenter(
    view: View,
    private val productRepository: DomainProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val basketRepository: BasketRepository,
) : Presenter(view) {
    private var basket = Basket(loadUnit = TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE)
    private var products = Products()
    private var recentProducts = RecentProducts()
    private var currentPage: PageNumber = PageNumber(sizePerPage = LOAD_PRODUCT_SIZE_AT_ONCE)

    override fun fetchAll() {
        fetchProducts()
        fetchRecentProducts()
        fetchBasket()
    }

    override fun fetchProducts() {
        basket += basketRepository.getProductByPage(currentPage)

        view.updateProducts(basket.getItemsByUnit().map { it.toUi() })
        view.updateLoadMoreVisible()
    }

    override fun fetchRecentProducts() {
        recentProducts = RecentProducts(recentProductRepository.getPartially(RECENT_PRODUCT_SIZE))
        view.updateRecentProducts(recentProducts.getItems().map { it.toUi() })
    }

    override fun fetchBasket() {
        basket = basketRepository.getProductByPage(currentPage)
    }

    override fun getMoreProducts() {
        currentPage = ++currentPage
        fetchProducts()
    }

    private fun View.updateLoadMoreVisible() {
        if (products.canLoadMore()) {
            showLoadMoreButton()
        } else {
            hideLoadMoreButton()
        }
    }

    override fun inquiryProductDetail(product: UiProduct) {
        val recentProduct = RecentProduct(product = product.toDomain())
        recentProducts += recentProduct

        view.updateRecentProducts(recentProducts.getItems().map { it.toUi() })
        view.showProductDetail(product)

        recentProductRepository.add(recentProduct)
    }

    override fun inquiryRecentProductDetail(recentProduct: UiRecentProduct) {
        view.showProductDetail(recentProduct.product)
        recentProductRepository.add(recentProduct.toDomain())
    }

    override fun openBasket() {
        view.navigateToBasketScreen()
    }

    override fun addBasketProduct(product: UiProduct) {
        val newProduct = product.toDomain()
        basket += newProduct
        basketRepository.plusProductCount(newProduct)

        updateBasketProducts()
    }

    override fun removeBasketProduct(product: UiProduct) {
        val removingProduct = product.toDomain()
        basket -= removingProduct
        basketRepository.minusProductCount(removingProduct)

        updateBasketProducts()
    }

    private fun updateBasketProducts() {
        view.updateBasketProductCount(basket.productTotalCount)
        view.updateProducts(basket.getItemsByUnit().map { it.toUi() })
    }

    companion object {
        private const val RECENT_PRODUCT_SIZE = 10
        private const val LOAD_PRODUCT_SIZE_AT_ONCE = 20
        private const val PRODUCT_SIZE_FOR_HAS_NEXT = 1
        private const val TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE =
            LOAD_PRODUCT_SIZE_AT_ONCE + PRODUCT_SIZE_FOR_HAS_NEXT
    }
}
