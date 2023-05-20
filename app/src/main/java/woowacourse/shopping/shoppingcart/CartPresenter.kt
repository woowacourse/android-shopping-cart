package woowacourse.shopping.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.Cart
import model.CartPage
import model.CartPagination
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.util.toDomainModel
import woowacourse.shopping.util.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val repository: ShoppingRepository,
) : CartContract.Presenter {

    private val cartPage: CartPagination = CartPage(
        cart = Cart()
    )
    private val cart: Cart
        get() = cartPage.cart

    private val _showingProducts: MutableLiveData<List<CartProductUiModel>> = MutableLiveData(
        cartPage.showingProducts.map { it.toUiModel() }
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
        val products = repository.selectShoppingCartProducts()
        cart.addAll(products)
        updatePage(cartPage)
    }

    override fun removeShoppingCartProduct(id: Int) {
        repository.deleteFromShoppingCart(id)
        cart.remove(id)
        updatePage(cartPage)
    }

    override fun plusShoppingCartProductCount(id: Int) {
        cart.plusProductCount(id)

        repository.insertToShoppingCart(
            id = id,
            count = cart.products.first { it.product.id == id }
                .count
                .value
        )
    }

    override fun minusShoppingCartProductCount(id: Int) {
        cart.minusProductCount(id)
        _showingProducts.value = cartPage.showingProducts.map { it.toUiModel() }

        repository.insertToShoppingCart(
            id = id,
            count = cart.products.first { it.product.id == id }
                .count
                .value
        )
    }

    override fun changeProductSelectedState(
        product: CartProductUiModel,
        isSelected: Boolean,
    ) {
        cart.changeSelectedState(
            product = product.toDomainModel(),
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
            callBack = ::updatePage,
            onReachedEndPage = view::showMessageReachedEndPage
        )
    }

    override fun moveToPrevPage() {
        cartPage.moveToPreviousPage(callBack = ::updatePage)
    }

    private fun updatePage(cartPage: CartPagination) {
        _showingProducts.value = cartPage.showingProducts.map { it.toUiModel() }
        _totalPrice.value = cartPage.totalPrice
        _currentPage.value = cartPage.currentPage.value
    }
}
