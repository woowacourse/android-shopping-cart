package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImp

class ProductListPresenter(
    private val view: ProductContract.View,
    private val repository: ProductRepository = ProductRepositoryImp()
) : ProductContract.Presenter {
    override fun loadProductItems() {
        val products = repository.getData()
        view.setProductItemsView(products)
    }
}
