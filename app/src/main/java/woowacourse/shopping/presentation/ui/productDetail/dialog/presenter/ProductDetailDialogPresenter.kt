package woowacourse.shopping.presentation.ui.productDetail.dialog.presenter

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailDialogPresenter(
    private val view: ProductDetailDialogContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ProductDetailDialogContract.Presenter {
    private var count = 1

    override fun addProductInCart(product: ProductInCartUiState) {
        shoppingCartRepository.addProductInCart(
            ProductInCart(
                product.product,
                product.quantity,
                product.isChecked,
            ),
        )
    }

    override fun addCountOfProductInCart(request: Operator, productInCart: ProductInCartUiState) {
        count += getQuantity(request)

        view.setCount(
            ProductInCartUiState(
                product = productInCart.product,
                quantity = count,
                isChecked = productInCart.isChecked,

            ),
        )
    }

    private fun getQuantity(request: Operator): Int {
        val quantity = Quantity()
        when (request) {
            Operator.PLUS -> quantity.add()
            Operator.MINUS -> quantity.subtract()
        }
        return quantity.amount
    }
}
