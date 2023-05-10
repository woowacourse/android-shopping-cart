package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.data.ProductRepositoryImp
import woowacourse.shopping.data.mapper.toUIModel

class ProductListPresenter(
    private val view: ProductContract.View,
    private val repository: ProductRepository = ProductRepositoryImp()
) : ProductContract.Presenter {
    override fun loadProductItems() {
        val products = repository.getData().map { it.toUIModel() }
        view.setProductItemsView(products)
    }
}
