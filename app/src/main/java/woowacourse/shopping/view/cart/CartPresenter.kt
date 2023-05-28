package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.data.server.ProductService
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartProductRepository
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartProductRepository: CartProductRepository,
    private val productRepository: ProductService,
) : CartContract.Presenter {
    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    override val totalPrice: LiveData<Int>
        get() = _totalPrice

    private val _totalCount: MutableLiveData<Int> = MutableLiveData(0)
    override val totalCount: LiveData<Int>
        get() = _totalCount

    private val cartPagination = CartPagination(PAGINATION_SIZE, cartProductRepository)

    private val currentCartProducts =
        convertIdToModel(cartPagination.nextItems()).toMutableList()

    override fun fetchProducts() {
        view.showProducts(
            currentCartProducts,
            cartPagination.isUndoItemsEnabled,
            cartPagination.isNextItemsEnabled,
            cartPagination.getPageNumber(),
        )
    }

    override fun removeProduct(id: Int) {
        val removedItem = currentCartProducts.find { it.id == id }
        cartProductRepository.remove(id)
        view.notifyRemoveItem(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)
        val getItems = cartPagination.currentItems()
        updateCartItems(getItems)
    }

    override fun fetchNextPage() {
        val getItems = cartPagination.nextItems()
        updateCartItems(getItems)
    }

    override fun fetchUndoPage() {
        val getItems = cartPagination.undoItems()
        updateCartItems(getItems)
    }

    override fun plusCount(id: Int) {
        cartProductRepository.updatePlus(id)
    }

    override fun subCount(id: Int) {
        cartProductRepository.UpdateSub(id)
    }

    override fun setupTotalPrice() {
        val cartModels = convertIdToModel(cartProductRepository.findCheckedItem())
        _totalPrice.value = cartModels.sumOf { it.count * it.product.price }
    }

    override fun updateItemCheck(id: Int, checked: Boolean) {
        cartProductRepository.updateCheckState(id, checked)
        currentCartProducts
            .indexOfFirst { it.id == id }
            .let { currentCartProducts[it] = currentCartProducts[it].copy(check = checked) }
    }

    override fun setupTotalCount() {
        val cartModels = convertIdToModel(cartProductRepository.findCheckedItem())
        _totalCount.value = cartModels.sumOf { it.count }
    }

    override fun judgeCheck(checked: Boolean) {
        if (checked) {
            setAllCheck()
        } else {
            setAllUncheck()
        }
    }

    private fun setAllCheck() {
        currentCartProducts.forEach { cartProduct ->
            cartProductRepository.updateCheckState(cartProduct.id, true)
        }
        updateCartItems(cartPagination.currentItems())
    }

    private fun setAllUncheck() {
        currentCartProducts.forEach { cartProduct ->
            cartProductRepository.updateCheckState(cartProduct.id, false)
        }
        updateCartItems(cartPagination.currentItems())
    }

    override fun setAllCheckCondition(): Boolean {
        var count = 0
        currentCartProducts.forEach {
            if (it.check) count++
        }
        if (count == currentCartProducts.size) return true
        return false
    }

    private fun updateCartItems(getItems: List<CartProduct>) {
        if (getItems.isNotEmpty()) {
            currentCartProducts.clear()
            currentCartProducts.addAll(convertIdToModel(getItems))
            fetchProducts()
        }
    }

    private fun convertIdToModel(cartProducts: List<CartProduct>): List<CartProductModel> {
        return cartProducts.map { cartProduct ->
            val product = productRepository.find(cartProduct.id)
            val productModel = product.toUiModel()
            CartProductModel(cartProduct.id, productModel, cartProduct.count, cartProduct.check)
        }
    }

    override fun handleNextStep(itemId: Int) {
        when (itemId) {
            android.R.id.home -> {
                view.handleBackButtonClicked()
            }
        }
    }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
