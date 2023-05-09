package woowacourse.shopping.shopping.contract.presenter

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.shopping.contract.ShoppingContract

class ShoppingPresenter(private val view: ShoppingContract.View, private val repository: ProductRepository): ShoppingContract.Presenter{
    override fun initProductsRecyclerView() {
        val productsData = repository.getAll().map { product: Product ->  product.toUIModel() }
        view.initProductsRecyclerView(productsData)
    }

    override fun onItemClick(data: ProductUIModel) {
        view.onItemClick(data)
    }
}
