package woowacourse.shopping.presentation.cart

import woowacourse.shopping.Products
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    productRepository: ProductRepository,
) : CartContract.Presenter {

    private val paging = Paging(cartRepository, productRepository)

    init {
        setView()
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProductId(productModel.id)
    }

    override fun plusPage() {
        view.setPage(paging.plusPage().value)
    }

    override fun minusPage() {
        view.setPage(paging.minusPage().value)
    }

    override fun updateCart() {
        val cartProducts = paging.getCurrentPageProducts()
        view.setCartItems(cartProducts.toPresentation())
    }

    override fun updatePlusButtonState() {
        view.setUpPlusPageButtonState(paging.isPlusPageAble())
    }

    override fun updateMinusButtonState() {
        view.setUpMinusPageButtonState(paging.isMinusPageAble())
    }

    private fun setView() {
        view.setPage(paging.page.value)
        updateCart()
        updatePlusButtonState()
        updateMinusButtonState()
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }
}
