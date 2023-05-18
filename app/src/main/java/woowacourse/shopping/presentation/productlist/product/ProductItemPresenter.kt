package woowacourse.shopping.presentation.productlist.product

import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository

class ProductItemPresenter(
    private val view: ProductItemContract.View,
    private val cartRepository: CartRepository,
) : ProductItemContract.Presenter {
    override fun loadProductCount(product: ProductModel) {
        val count = cartRepository.getCartProductInfoById(product.id)?.count ?: 0
        view.setProductCount(count)
        setAddCartEnableByCount(count)
    }

    override fun changeProductCount(
        product: ProductModel,
        count: Int,
    ) {
        updateCartState(product, count)
        setAddCartEnableByCount(count)
    }

    override fun putProductInCart(product: ProductModel) {
        cartRepository.putProductInCart(product.id)
        view.setAddCartEnable(false)
    }

    private fun setAddCartEnableByCount(count: Int) {
        if (count == DELETE_CONDITION) {
            view.setAddCartEnable(true)
        } else {
            view.setAddCartEnable(false)
        }
    }

    private fun updateCartState(product: ProductModel, count: Int) {
        if (count == DELETE_CONDITION) {
            cartRepository.deleteCartProductId(product.id)
        } else {
            cartRepository.updateCartProductCount(product.id, count)
        }
    }

    companion object {
        private const val DELETE_CONDITION = 0
    }
}
