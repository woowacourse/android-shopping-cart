package woowacourse.shopping.view.shoppingcart

import com.shopping.repository.CartProductRepository
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.mapper.toDomain
import woowacourse.shopping.uimodel.mapper.toUIModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val cartProductRepository: CartProductRepository
) : ShoppingCartContract.Presenter {
    override lateinit var cartProducts: List<CartProductUIModel>

    override fun setRecentProducts() {
        cartProducts = cartProductRepository.getAll().map { it.toUIModel() }
    }

    override fun removeCartProduct(productUIModel: ProductUIModel) {
        cartProductRepository.remove(CartProductUIModel(productUIModel).toDomain())

        val index = getIndex(productUIModel)
        cartProducts = cartProducts - cartProducts[index]
        view.removeCartProduct(cartProducts, index)
    }

    private fun getIndex(product: ProductUIModel): Int {
        return cartProducts.indices.find { index ->
            cartProducts[index].productUIModel.id == product.id
        } ?: throw IllegalStateException("해당 값을 찾을 수 없습니다.")
    }
}
