package woowacourse.shopping.feature.cart

import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.CartProductUiModel

class CartPresenter(
    val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    override fun loadCartProduct() {
        val updatedProducts = cartRepository.getAll().map {
            it.toPresentation().toItemModel { position ->
                view.deleteCartProductFromScreen(position)
            }
        }
        view.changeCartProducts(updatedProducts)
    }

    override fun deleteCartProduct(cartProduct: CartProductUiModel) {
        cartRepository.deleteProduct(cartProduct.toDomain())
        loadCartProduct()
    }
}
