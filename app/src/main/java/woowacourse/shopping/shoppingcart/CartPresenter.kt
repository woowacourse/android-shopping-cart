package woowacourse.shopping.shoppingcart

import model.Cart
import model.CartPage
import model.CartPagination
import model.CartProduct
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.util.toDomainModel
import woowacourse.shopping.util.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val repository: ShoppingRepository,
    override val cartPage: CartPagination = CartPage(
        cart = Cart()
    ),
) : CartContract.Presenter {

    private val cart: Cart
        get() = cartPage.cart

    private fun selectCartProducts(): List<CartProduct> {
        val products = repository.selectShoppingCartProducts(
            from = cart.products.size,
            count = CartPage.ITEM_COUNT_ON_EACH_PAGE
        )

        return products
    }

    override fun loadShoppingCartProducts() {
        val products = selectCartProducts()
        cart.addAll(products)

        view.setUpCartView(
            products = cart.products.map { it.toUiModel() },
            currentPage = cartPage.currentPage.value
        )
    }

    override fun removeShoppingCartProduct(product: CartProductUiModel) {
        repository.deleteFromShoppingCart(product.id)
        cartPage.cart.remove(product.toDomainModel())
        view.refreshCartProductView(
            products = cartPage.showingProducts.map { it.toUiModel() }
        )
    }

    override fun plusShoppingCartProductCount(product: CartProductUiModel) {
        cart.plusProductCount(product.toDomainModel())

        repository.insertToShoppingCart(
            id = product.id,
            count = cart.find(product.id)
                .count
                .value
        )
        view.refreshCartProductView(
            products = cartPage.showingProducts.map { it.toUiModel() }
        )
    }

    override fun minusShoppingCartProductCount(product: CartProductUiModel) {
        cart.minusProductCount(product.toDomainModel())

        repository.insertToShoppingCart(
            id = product.id,
            count = cart.find(product.id)
                .count
                .value
        )
        view.refreshCartProductView(
            products = cartPage.showingProducts.map { it.toUiModel() }
        )
    }

    override fun calcTotalPrice() {
        view.setUpTextTotalPriceView(cartPage.totalPrice)
    }

    override fun changeProductSelectedState(
        product: CartProductUiModel,
        isSelected: Boolean,
    ) {
        cart.changeSelectedState(
            product = product.toDomainModel(),
            isSelected = isSelected
        )
        view.refreshCartProductView(
            products = cartPage.showingProducts.map { it.toUiModel() }
        )
    }

    override fun changeProductsSelectedState(checked: Boolean) {
        cart.changeSelectedStateAll(checked)
        view.refreshCartProductView(
            products = cartPage.showingProducts.map { it.toUiModel() }
        )
    }

    override fun moveToNextPage() {
        cartPage.moveToNextPage(
            callBack = ::changePage,
            onReachedEndPage = ::onReachedEndPage
        )
    }

    private fun onReachedEndPage(cart: Cart) {
        val products = selectCartProducts()

        if (products.isEmpty()) {
            return view.showMessageReachedEndPage()
        }
        cart.addAll(products)
    }

    override fun moveToPrevPage() {
        cartPage.moveToPreviousPage(callBack = ::changePage)
    }

    private fun changePage(cartPage: CartPagination) {
        view.refreshCartProductView(
            products = cartPage.showingProducts.map { it.toUiModel() }
        )
        view.setUpTextPageNumber(cartPage.currentPage.value)
    }
}
