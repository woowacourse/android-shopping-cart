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
    private val checkState: MutableMap<ProductInCart, Boolean> = saveProductCheckState()
    private var pageNumber = PageNumber()

    init {
        saveProductCheckState()
    }

    private fun saveProductCheckState(): MutableMap<ProductInCart, Boolean> {
        val shoppingCart = shoppingCartRepository.getShoppingCart()
        val productCheckState: MutableMap<ProductInCart, Boolean> = mutableMapOf()

        shoppingCart.forEach {
            productCheckState[it] = it.isChecked
        }

        return productCheckState
    }

    override fun fetchProductsInCartByPage(page: Int) {
        val productsInCartByPage =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, page)

        view.setShoppingCart(productsInCartByPage.map { it.toProductInCartUiState() })
    }

    override fun fetchTotalPrice() {
        val productCheckState = saveProductCheckState()
        val isCheckedAll = productCheckState.values.all { it }
        val totalPrice = productCheckState.getSumOf(PRICE)
        val amount = productCheckState.getSumOf(AMOUNT)

        view.setTotalPrice(
            ShoppingCartUiState(
                isCheckedAll = isCheckedAll,
                totalPrice = totalPrice,
                amount = amount,
            ),
        )
    }

    override fun fetchTotalPriceByCheckAll(isChecked: Boolean) {
        val productCheckState = saveProductCheckState()
        val totalPrice = if (isChecked) productCheckState.getSumOf(PRICE) else NOTHING
        val amount = if (isChecked) productCheckState.getSumOf(AMOUNT) else NOTHING

        checkState.replaceAll { _, _ -> isChecked }

        view.setTotalPrice(
            ShoppingCartUiState(
                isCheckedAll = isChecked,
                totalPrice = totalPrice,
                amount = amount,
            ),
        )
        view.setShoppingCart(
            shoppingCartRepository.getShoppingCart().map { it.toProductInCartUiState() },
        )
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
                false,
            ),
        )
        val shoppingCart = shoppingCartRepository.getShoppingCart()

        view.setShoppingCart(shoppingCart.map { it.toProductInCartUiState() })
    }

    override fun deleteProductInCart(productId: Long) {
        val result = shoppingCartRepository.deleteProductInCart(productId)

        checkState.clear()
        checkState.putAll(saveProductCheckState())

        view.deleteItemInCart(result, productId)
    }

    override fun calculateTotalWithCheck(isChecked: Boolean, productInCart: ProductInCartUiState) {
        val shoppingCart = shoppingCartRepository.getShoppingCart()

        // 체크 분기처리하기
        // 전체체크 해제 시, 0원 else max
        // 단일 체크 해제 시, delete와 같은 효과
        // 단일 체크 해제 시, 전체 체크해제
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

    private fun MutableMap<ProductInCart, Boolean>.getSumOf(demand: String): Int {
        val sequence = this.asSequence().filter { it.value }

        return when (demand) {
            PRICE -> sequence.sumOf { it.key.getTotalPriceOfProduct() }
            AMOUNT -> sequence.sumOf { it.key.quantity }
            else -> throw IllegalArgumentException()
        }
    }

    companion object {
        private const val PRICE = "price"
        private const val AMOUNT = "amount"
        private const val NOTHING = 0
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
