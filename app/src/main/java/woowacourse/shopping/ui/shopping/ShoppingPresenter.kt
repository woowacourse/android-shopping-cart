package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.repository.DomainRecentProductRepository
import woowacourse.shopping.ui.mapper.toUi

class ShoppingPresenter(
    override val view: ShoppingContract.View,
    private val recentProductRepository: DomainRecentProductRepository
) : ShoppingContract.Presenter {
    override fun fetchRecentProducts() {
        view.updateRecentProducts(
            recentProductRepository.getPartially(RECENT_PRODUCT_SIZE).map { it.toUi() }
        )
    }

    companion object {
        private const val RECENT_PRODUCT_SIZE = 10
    }
}
