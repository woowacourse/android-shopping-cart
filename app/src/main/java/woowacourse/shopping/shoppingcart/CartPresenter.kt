package woowacourse.shopping.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.Cart
import woowacourse.shopping.database.ShoppingCache
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.util.toCartProductUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val shoppingCache: ShoppingCache,
    private val cartPage: CartPageHandler = CartPage(
        cart = Cart()
    )
) : CartContract.Presenter {

    private val cart: Cart
        get() = cartPage.cart

    private val _showingProducts: MutableLiveData<List<CartProductUiModel>> = MutableLiveData(
        cartPage.showingProducts.map { it.toCartProductUiModel() }
    )
    val showingProducts: LiveData<List<CartProductUiModel>>
        get() = _showingProducts

    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(
        cartPage.totalPrice
    )
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(
        cartPage.currentPage.value
    )
    val currentPage: LiveData<Int>
        get() = _currentPage

    override fun loadShoppingCartProducts() {
        val products = shoppingCache.selectShoppingCartProducts()
        cart.addAll(products)
        updatePage(cartPage)
    }

    override fun removeShoppingCartProduct(id: Int) {
        shoppingCache.deleteFromShoppingCart(id)
        cart.remove(id)
        updatePage(cartPage)
    }

    override fun plusShoppingCartProductCount(id: Int) {
        cart.plusProductCount(id)

        shoppingCache.insertToShoppingCart(
            id = id,
            count = cart.products.first { it.product.id == id }
                .count
                .value
        )
        updatePage(cartPage)
    }

    override fun minusShoppingCartProductCount(id: Int) {
        cart.minusProductCount(id)
        _showingProducts.value = cartPage.showingProducts.map { it.toCartProductUiModel() }

        shoppingCache.insertToShoppingCart(
            id = id,
            count = cart.products.first { it.product.id == id }
                .count
                .value
        )
        updatePage(cartPage)
    }

    override fun changeProductSelectedState(
        id: Int,
        isSelected: Boolean,
    ) {
        cart.changeSelectedState(
            id = id,
            isSelected = isSelected
        )
        updatePage(cartPage)
    }

    override fun changeProductsSelectedState(checked: Boolean) {
        cart.changeSelectedStateAll(checked)
        updatePage(cartPage)
    }

    override fun moveToNextPage() {
        cartPage.moveToNextPage(
            onPageChanged = ::updatePage,
            onReachedEndPage = view::showMessageReachedEndPage
        )
    }

    override fun moveToPrevPage() {
        cartPage.moveToPreviousPage(onPageChanged = ::updatePage)
    }

    private fun updatePage(cartPage: CartPageHandler) {
        _showingProducts.value = cartPage.showingProducts.map { it.toCartProductUiModel() }
        _totalPrice.value = cartPage.totalPrice
        _currentPage.value = cartPage.currentPage.value
    }
}
