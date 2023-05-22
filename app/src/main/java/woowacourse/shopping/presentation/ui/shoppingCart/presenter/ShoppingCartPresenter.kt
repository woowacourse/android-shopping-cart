package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.common.uimodel.Operator.MINUS
import woowacourse.shopping.presentation.ui.common.uimodel.Operator.PLUS
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    private var pageNumber = PageNumber()

    val _isChecked: MutableLiveData<Boolean> = MutableLiveData(true)

    // val isChecked: LiveData<Boolean> get() = _isChecked
    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice
    private val _totalAmount: MutableLiveData<Int> = MutableLiveData(0)
    val totalAmount: LiveData<Int> get() = _totalAmount

    override fun getShoppingCart(page: Int) {
        val shoppingCart = shoppingCartRepository.getShoppingCart()
        val shoppingCartByPage =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, page)

        _totalAmount.value = shoppingCart.sumOf { it.quantity }
        _totalPrice.value = shoppingCart.sumOf { it.getTotalPriceOfProduct() }

        view.setShoppingCart(shoppingCartByPage.map { it.toUiState() })
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

    override fun addCountOfProductInCart(request: Operator, productInCart: ProductInCartUiState) {
        val quantity = Quantity()
        when (request) {
            PLUS -> quantity.add()
            MINUS -> quantity.subtract()
        }

        shoppingCartRepository.addProductInCart(
            ProductInCart(
                productInCart.product,
                quantity.amount,
            ),
        )
        val shoppingCart = shoppingCartRepository.getShoppingCart()

        _totalAmount.value = shoppingCart.sumOf { it.quantity }
        _totalPrice.value = shoppingCart.sumOf { it.getTotalPriceOfProduct() }
        view.setShoppingCart(shoppingCart.map { it.toUiState() })
    }

    override fun deleteProductInCart(productId: Long): Boolean {
        val result = shoppingCartRepository.deleteProductInCart(productId)
        val shoppingCart = shoppingCartRepository.getShoppingCart()
        _totalAmount.value = shoppingCart.sumOf { it.quantity }
        _totalPrice.value = shoppingCart.sumOf { it.getTotalPriceOfProduct() }

        return result
    }

    override fun calculateTotalWithCheck(isChecked: Boolean, productInCart: ProductInCartUiState) {
        val shoppingCart = shoppingCartRepository.getShoppingCart()

        when (isChecked) {
            false -> calculateUnChecked(shoppingCart)
            true -> calculateChecked(shoppingCart)
        }
    }

    private fun calculateUnChecked(shoppingCart: List<ProductInCart>) {
        _isChecked.value = false
        _totalAmount.value = _totalAmount.value?.minus(shoppingCart.sumOf { it.quantity })
        _totalPrice.value =
            _totalPrice.value?.minus(shoppingCart.sumOf { it.getTotalPriceOfProduct() })
    }

    private fun calculateChecked(shoppingCart: List<ProductInCart>) {
        _totalAmount.value = _totalAmount.value?.plus(shoppingCart.sumOf { it.quantity })
        _totalPrice.value =
            _totalPrice.value?.plus(shoppingCart.sumOf { it.getTotalPriceOfProduct() })
    }

    private fun ProductInCart.toUiState(): ProductInCartUiState = ProductInCartUiState(
        product = this.product,
        quantity = this.quantity,
        isChecked = _isChecked.value ?: true,
    )

    companion object {
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
