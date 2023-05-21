package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Count
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct
import woowacourse.shopping.util.secondOrNull
import kotlin.concurrent.thread

class ShoppingPresenter(
    override val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val basketRepository: BasketRepository,
    private var hasNext: Boolean = false,
    private var lastId: Int = -1,
    private var totalProducts: List<UiProduct> = listOf(),
    private var recentProducts: List<UiRecentProduct> = listOf(),
    private var basket: Basket = Basket(basketRepository.getAll())
) : ShoppingContract.Presenter {

    init {
        fetchTotalBasketCount()
    }

    private fun fetchBasketCount() {
        totalProducts = totalProducts.map {
            UiProduct(
                it.id,
                it.name,
                it.price,
                it.imageUrl,
                basket.getCountByProductId(it.id)
            )
        }
    }

    override fun updateBasket() {
        basket = Basket(basketRepository.getAll())
        fetchBasketCount()
        fetchTotalBasketCount()
        view.updateProducts(totalProducts)
    }

    override fun fetchTotalBasketCount() {
        view.updateTotalBasketCount(basket.products.fold(0) { acc, basketProduct -> acc + basketProduct.count.value })
    }

    override fun addBasketProduct(product: Product) {
        val addedProduct = BasketProduct(count = Count(1), product = product)
        basketRepository.add(addedProduct)
        basket = basket.add(addedProduct)
        fetchBasketCount()
        fetchTotalBasketCount()
        view.updateProducts(totalProducts)
    }

    override fun removeBasketProduct(product: Product) {
        val removedProduct = BasketProduct(count = Count(1), product = product)
        basketRepository.minus(removedProduct)
        basket = basket.delete(removedProduct)
        fetchBasketCount()
        fetchTotalBasketCount()
        view.updateProducts(totalProducts)
    }

    override fun fetchProducts() {
        var products = productRepository
            .getPartially(TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE, lastId)
            .map { it.toUi() }
        lastId = products.maxOfOrNull { it.id } ?: -1
        hasNext = checkHasNext(products)
        lastId -= if (hasNext) 1 else 0
        if (hasNext) products = products.dropLast(1)
        totalProducts += products
        fetchBasketCount()
        view.updateProducts(totalProducts)
    }

    private fun checkHasNext(products: List<UiProduct>): Boolean =
        products.size == TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE

    override fun fetchRecentProducts() {
        recentProducts = recentProductRepository.getPartially(RECENT_PRODUCT_SIZE)
            .map { it.toUi() }
        view.updateRecentProducts(recentProducts)
    }

    override fun inquiryProductDetail(product: UiProduct) {
        val previousProduct =
            if (recentProducts.firstOrNull()?.product == product) recentProducts.secondOrNull()?.product else recentProducts.firstOrNull()?.product
        view.showProductDetail(currentProduct = product, previousProduct = previousProduct)
        thread { recentProductRepository.add(product.toDomain()) }
    }

    override fun fetchHasNext() {
        view.updateMoreButtonState(hasNext)
    }

    companion object {
        private const val RECENT_PRODUCT_SIZE = 10
        private const val LOAD_PRODUCT_SIZE_AT_ONCE = 20
        private const val PRODUCT_SIZE_FOR_HAS_NEXT = 1
        private const val TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE =
            LOAD_PRODUCT_SIZE_AT_ONCE + PRODUCT_SIZE_FOR_HAS_NEXT
    }
}
