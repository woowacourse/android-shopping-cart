package woowacourse.shopping.feature.product.detail

import com.example.domain.repository.CartRepository
import woowacourse.shopping.model.CartProductState.Companion.MAX_COUNT_VALUE
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.mapper.toDomain

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductState?,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {

    private var count = MIN_COUNT_VALUE

    override fun loadProduct() {
        if (!isValidProduct()) return
        product!!

        view.setViewContent(product)
    }

    override fun addCartProduct(count: Int) {
        if (!isValidProduct()) return
        product!!

        cartRepository.addProduct(product.toDomain(), count)
        view.showCart()
        view.closeProductDetail()
    }

    override fun minusCount() {
        count--
        if (count < MIN_COUNT_VALUE) count++
        view.setCount(count)
    }

    override fun plusCount() {
        count++
        if (MAX_COUNT_VALUE < count) count--
        view.setCount(count)
    }

    private fun isValidProduct(): Boolean {
        if (product == null) {
            view.showAccessError()
            view.closeProductDetail()
            return false
        }
        return true
    }
}
