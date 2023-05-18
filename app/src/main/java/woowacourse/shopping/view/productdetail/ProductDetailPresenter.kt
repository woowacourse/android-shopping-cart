package woowacourse.shopping.view.productdetail

import com.shopping.domain.RecentProduct
import com.shopping.repository.CartProductRepository
import com.shopping.repository.RecentProductsRepository

import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain

class ProductDetailPresenter(
    override val product: ProductUIModel,
    private val cartProductRepository: CartProductRepository,
    private val recentProductsRepository: RecentProductsRepository
) : ProductDetailContract.Presenter {

    override fun saveRecentProduct() {
        return recentProductsRepository.insert(RecentProduct(product.toDomain()))
    }

    override fun showDialog(dialog: CountSelectDialog) {
        dialog.show(product)
    }
}
