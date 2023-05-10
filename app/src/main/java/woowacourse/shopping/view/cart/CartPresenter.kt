package woowacourse.shopping.view.cart

import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {
    override fun fetchProducts() {
        val cartProducts = cartRepository.findAll().map { ProductMockRepository.find(it.id) }
        view.showProducts(cartProducts.map { it.toUiModel() })
    }

    override fun removeProduct(id: Int) {
        cartRepository.remove(id)
        view.updateProducts()
    }
}
