package woowacourse.shopping.productcatalogue

import woowacourse.shopping.datas.ProductRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.mapper.toUIModel

class ProductCataloguePresenter(
    private val view: ProductCatalogueContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentRepository,
) : ProductCatalogueContract.Presenter {

    override fun getRecentProduct() {
        val recentProducts =
            recentProductRepository.getAll()
                .sortedBy { it.time }
                .takeLast(RECENT_PRODUCT_UNIT_SIZE)
                .map { it.toUIModel() }
        view.setRecentProductList(recentProducts)
    }

    override fun readMoreOnClick(unitSize: Int, page: Int) {
        productRepository.getUnitData(unitSize, page).map { it.toUIModel() }
        view.notifyDataChanged()
    }

    companion object {
        private const val RECENT_PRODUCT_UNIT_SIZE = 10
    }
}
