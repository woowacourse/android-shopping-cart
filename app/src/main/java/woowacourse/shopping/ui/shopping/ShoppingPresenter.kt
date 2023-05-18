package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct
import kotlin.concurrent.thread

class ShoppingPresenter(
    override val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository
) : ShoppingContract.Presenter {
    private var hasNext: Boolean = false
    private var lastId: Int = -1
    private var totalProducts: List<UiProduct> = listOf()
    private var recentProducts: List<UiRecentProduct> = listOf()

    override fun fetchProducts() {
        var products = productRepository
            .getPartially(TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE, lastId)
            .map { it.toUi() }
        lastId = products.maxOfOrNull { it.id } ?: -1
        hasNext = checkHasNext(products)
        lastId -= if (hasNext) 1 else 0
        if (hasNext) products = products.dropLast(1)
        totalProducts += products
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
        view.showProductDetail(product)
        thread { recentProductRepository.add(product.toDomain()) }
    }

    override fun inquiryRecentProductDetail(recentProduct: UiProduct) {
        view.showProductDetail(recentProduct)
        thread { recentProductRepository.add(recentProduct.toDomain()) }
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
