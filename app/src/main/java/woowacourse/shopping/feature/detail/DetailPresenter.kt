package woowacourse.shopping.feature.detail

import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel

class DetailPresenter(
    private val view: DetailContract.View,
    private val cartRepository: CartRepository,
    product: ProductUiModel
) : DetailContract.Presenter {
    private var _product: ProductUiModel = product
    override val product: ProductUiModel
        get() = _product

    private var count: Int = 1

    override fun increaseCount() {
        count++
    }

    override fun decreaseCount() {
        count--
    }

    override fun selectCount() {
        view.showSelectCountScreen(product)
    }

    override fun addCart() {
        val newCount = product.count + count
        _product = _product.copy(count = newCount)
        cartRepository.addProduct(_product.toDomain(), newCount)
        view.showCartScreen()
    }
}
