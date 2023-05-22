package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    lateinit var shoppingCart: List<ProductInCart>
    private var pageNumber = PageNumber()

    private val _isChecked: MutableLiveData<Boolean> = MutableLiveData(true)
    val isChecked: LiveData<Boolean> get() = _isChecked
    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice
    private val _totalAmount: MutableLiveData<Int> = MutableLiveData(0)
    val totalAmount: LiveData<Int> get() = _totalAmount

    override fun getShoppingCart(page: Int) {
        val shoppingCart =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, page)

        _totalAmount.value = shoppingCart.sumOf { it.quantity }
        _totalPrice.value = shoppingCart.sumOf { it.getTotalPriceOfProduct() }
        view.setShoppingCart(shoppingCart.map { it.toUiState() })
    }

    override fun setPageNumber() {
        view.setPage(pageNumber.value)
    }

    private fun getPage() {
        val shoppingCart =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, pageNumber.value)
                .map { it.toUiState() }
        view.setShoppingCart(shoppingCart)
    }

    private fun goOtherPage() {
        checkPageMovement()
        setPageNumber()
        getPage()
    }

    override fun goNextPage() {
        pageNumber = pageNumber.nextPage()
        goOtherPage()
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        goOtherPage()
    }

    override fun checkPageMovement() {
        val size = shoppingCartRepository.getShoppingCartSize()
        val nextEnable = size > pageNumber.value * SHOPPING_CART_ITEM_COUNT
        val previousEnable = pageNumber.value > 1
        view.setPageButtonEnable(previousEnable, nextEnable)
    }

    override fun addCountOfProductInCart(request: Operator, productInCart: Product) {
        val quantity = Quantity()
        when (request) {
            Operator.PLUS -> quantity.add()
            Operator.MINUS -> quantity.subtract()
        }

        shoppingCartRepository.addProductInCart(ProductInCart(productInCart, quantity.amount))

        val shoppingCart = shoppingCartRepository.getShoppingCart().map { it.toUiState() }

        _totalAmount.value = shoppingCart.sumOf { it.quantity }
        _totalPrice.value = shoppingCart.sumOf { it.getTotalPriceOfProduct() }
        view.setShoppingCart(shoppingCart)
    }

    override fun deleteProductInCart(productId: Long): Boolean {
        val result = shoppingCartRepository.deleteProductInCart(productId)
        val shoppingCart =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, pageNumber.value)

        _totalAmount.value = shoppingCart.sumOf { it.quantity }
        _totalPrice.value = shoppingCart.sumOf { it.getTotalPriceOfProduct() }

        return result
    }

    private fun ProductInCart.toUiState(): ProductInCartUiState = ProductInCartUiState(
        product = this.product,
        quantity = this.quantity,
        isChecked = isChecked.value ?: true,
    )

    companion object {
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
