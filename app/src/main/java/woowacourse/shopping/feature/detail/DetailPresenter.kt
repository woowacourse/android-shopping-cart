package woowacourse.shopping.feature.detail

import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel

class DetailPresenter(
    val view: DetailContract.View,
    private val cartRepository: CartRepository,
    product: ProductUiModel
) : DetailContract.Presenter {
    override var product: ProductUiModel = product
        private set

    override fun addCart() {
        cartRepository.addProduct(product.toDomain())
        view.showCartScreen()
    }

    override fun exit() {
        view.exitDetailScreen()
    }
}