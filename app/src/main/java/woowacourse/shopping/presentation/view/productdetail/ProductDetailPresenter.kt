package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.data.ProductRepositoryImp
import woowacourse.shopping.data.mapper.toUIModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val repository: ProductRepository = ProductRepositoryImp()
) :
    ProductDetailContract.Presenter {
    override fun loadProductInfoById(id: Long) {
        val product = repository.getDataById(id)
        if (product.id == -1L) {
            view.handleErrorView()
            return
        }
        view.setProductInfoView(product.toUIModel())
    }
}
