package woowacourse.shopping.productcatalogue

import woowacourse.shopping.datas.ProductRepository
import woowacourse.shopping.uimodel.ProductUIModel

class ProductCataloguePresenter(
    private val view: ProductCatalogueContract.View,
) : ProductCatalogueContract.Presenter {

    override fun productOnClick(): (ProductUIModel) -> Unit =
        {
            view.showProductDetailPage(it)
        }

    override fun readMoreOnClick(): (ProductRepository, Int, Int) -> Unit =
        { productRepository: ProductRepository, unitSize: Int, page: Int ->
            productRepository.getUnitData(unitSize, page)
            view.notifyDataChanged()
        }
}
