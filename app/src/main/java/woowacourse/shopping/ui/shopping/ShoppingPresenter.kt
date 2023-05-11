package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.repository.DomainProductRepository
import woowacourse.shopping.domain.repository.DomainRecentProductRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiProduct
import kotlin.concurrent.thread

class ShoppingPresenter(
    override val view: ShoppingContract.View,
    private val productRepository: DomainProductRepository,
    private val recentProductRepository: DomainRecentProductRepository
) : ShoppingContract.Presenter {
    override fun fetchProducts() {
        view.updateProducts(
            productRepository.getAll().map { it.toUi() }
        )
    }

    override fun fetchRecentProducts() {
        view.updateRecentProducts(
            recentProductRepository.getPartially(RECENT_PRODUCT_SIZE).map { it.toUi() }
        )
    }

    override fun inquiryRecentProduct(product: UiProduct) {
        view.showProductDetail(product)
        thread { recentProductRepository.add(product.toDomain()) }
    }

    companion object {
        private const val RECENT_PRODUCT_SIZE = 10
    }
}
