package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.common.uimodel.Operator.MINUS
import woowacourse.shopping.presentation.ui.common.uimodel.Operator.PLUS
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ShoppingCartUiState

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    private val checkState: MutableMap<Long, Boolean> = saveProductCheckState()
    private var pageNumber = PageNumber()

    private fun saveProductCheckState(): MutableMap<Long, Boolean> {
        val shoppingCart = shoppingCartRepository.getShoppingCart()
        val productCheckState: MutableMap<Long, Boolean> = mutableMapOf()

        shoppingCart.forEach {
            productCheckState[it.product.id] = it.isChecked
        }

        return productCheckState
    }

    override fun fetchProductsInCartByPage(page: Int) {
        val productsInCartByPage =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, page)

        view.setShoppingCart(productsInCartByPage.map { it.toProductInCartUiState() })
    }

    override fun fetchTotalPrice() {
        val isCheckedAll = checkState.values.all { it }
        val totalPrice = getSumOf(PRICE)
        val amount = getSumOf(AMOUNT)

        view.setTotalPrice(
            ShoppingCartUiState(
                isCheckedAll = isCheckedAll,
                totalPrice = totalPrice,
                amount = amount,
            ),
        )
    }

    override fun fetchCheckState(isChecked: Boolean, productInCart: ProductInCartUiState) {
        checkState[productInCart.product.id] = isChecked
    }

    override fun fetchTotalPriceByCheckAll(isChecked: Boolean) {
        checkState.replaceAll { _, _ -> isChecked }

        view.setShoppingCart(
            shoppingCartRepository.getShoppingCart().map { it.toProductInCartUiState() },
        )
    }

    override fun addCountOfProductInCart(request: Operator, productInCart: ProductInCartUiState) {
        shoppingCartRepository.addProductInCart(
            ProductInCart(
                productInCart.product,
                getQuantity(request),
                productInCart.isChecked,
            ),
        )

        val shoppingCart =
            shoppingCartRepository.getShoppingCart().map { it.toProductInCartUiState() }

        view.setShoppingCart(shoppingCart)
    }

    private fun getQuantity(request: Operator): Int {
        val quantity = Quantity()
        when (request) {
            PLUS -> quantity.add()
            MINUS -> quantity.subtract()
        }
        return quantity.amount
    }

    override fun deleteProductInCart(productId: Long) {
        val result = shoppingCartRepository.deleteProductInCart(productId)

        checkState.clear()
        checkState.putAll(saveProductCheckState())

        view.deleteItemInCart(result, productId)
    }

    private fun getSumOf(demand: String): Int {
        val shoppingCart = shoppingCartRepository.getShoppingCart().filter {
            checkState[it.product.id] == true
        }

        return when (demand) {
            PRICE -> shoppingCart.sumOf { it.getTotalPriceOfProduct() }
            AMOUNT -> shoppingCart.sumOf { it.quantity }
            else -> throw IllegalArgumentException()
        }
    }

    override fun setPageNumber() {
        view.setPage(pageNumber.value)
    }

    private fun getPage() {
        val shoppingCart =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, pageNumber.value)
                .map { it.toProductInCartUiState() }
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

    private fun ProductInCart.toProductInCartUiState(): ProductInCartUiState = ProductInCartUiState(
        product = this.product,
        quantity = this.quantity,
        isChecked = checkState.values.all { it },
    )

    companion object {
        private const val PRICE = "price"
        private const val AMOUNT = "amount"
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
