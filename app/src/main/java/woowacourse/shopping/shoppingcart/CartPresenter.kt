package woowacourse.shopping.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private fun selectCartProducts(): List<CartProduct> {
        val products = repository.selectShoppingCartProducts(
            from = cartPage.cart.products.size,
            count = CartPage.ITEM_COUNT_ON_EACH_PAGE
        )

        return products
    }

    override fun loadShoppingCartProducts() {
        val products = selectCartProducts()
        cart.addAll(products)
        updatePage(cartPage)
    }

    override fun removeShoppingCartProduct(product: CartProductUiModel) {
        repository.deleteFromShoppingCart(product.id)
        cart.remove(product.toDomainModel())
    }

    override fun plusShoppingCartProductCount(product: CartProductUiModel) {
        cart.plusProductCount(product.toDomainModel())

        repository.insertToShoppingCart(
            id = product.id,
            count = cart.find(product.id)
                .count
                .value
        )
    }

    override fun minusShoppingCartProductCount(product: CartProductUiModel) {
        cart.minusProductCount(product.toDomainModel())
        _showingProducts.value = cartPage.showingProducts.map { it.toUiModel() }

        repository.insertToShoppingCart(
            id = product.id,
            count = cart.find(product.id)
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
        cartPage.moveToPreviousPage(callBack = ::updatePage)
    }

    private fun updatePage(cartPage: CartPagination) {
        _showingProducts.value = cartPage.showingProducts.map { it.toUiModel() }
        _totalPrice.value = cartPage.totalPrice
        _currentPage.value = cartPage.currentPage.value
    }
}
