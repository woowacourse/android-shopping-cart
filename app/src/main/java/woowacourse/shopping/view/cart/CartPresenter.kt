package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.cartsystem.CartPageStatus
import woowacourse.shopping.domain.pagination.CartPagination
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.cartsystem.CartSystem
import woowacourse.shopping.domain.cartsystem.CartSystemResult
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.model.toDomain
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : CartContract.Presenter {
    private val cartPagination = CartPagination(PAGINATION_SIZE, cartRepository)
    private val cartSystem = CartSystem(productRepository)
    private val cartItems: MutableList<CartViewItem>

    private var _cartSystemResult = MutableLiveData(CartSystemResult(0, 0))
    private var _cartPageStatus = MutableLiveData(
        CartPageStatus(
            isPrevEnabled = false,
            isNextEnabled = false,
            0
        )
    )
    private var _isCheckedAll = MutableLiveData(false)

    override val cartSystemResult: LiveData<CartSystemResult>
        get() = _cartSystemResult
    override val cartPageStatus: LiveData<CartPageStatus>
        get() = _cartPageStatus
    override val isCheckedAll: LiveData<Boolean>
        get() = _isCheckedAll

    init {
        val models = convertCartProductToModels(cartPagination.nextItems())
        cartItems = (
            models.map { CartViewItem.CartProductItem(it) } +
                CartViewItem.PaginationItem(cartPagination.status)
            ).toMutableList()
    }

    override fun fetchProducts() {
        view.showProducts(cartItems)
    }

    override fun removeProduct(id: Int) {
        val nextItemExist = cartPagination.isNextEnabled
        cartRepository.remove(id)
        cartItems.removeIf { it is CartViewItem.CartProductItem && it.product.id == id }
        _cartSystemResult.value = cartSystem.removeProduct(id)
        _isCheckedAll.value = getIsCheckedAll()

        // 남은 자리 페이지 뒷 상품으로 다시 채우기
        if (nextItemExist) addNextProduct()
        view.showChangedItems()
    }

    private fun addNextProduct() {
        cartItems.removeLast()
        getNextCartProductModel()?.let {
            cartItems.add(CartViewItem.CartProductItem(it))
        }
        cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
        _isCheckedAll.value = getIsCheckedAll()
    }

    private fun getIsCheckedAll() =
        cartSystem.selectedProducts.containsAll(convertItemsToCartProducts(cartItems))

    private fun getNextCartProductModel(): CartProductModel? {
        val product = cartPagination.currentLastItem() ?: return null
        return product.toUiModel(
            cartSystem.isSelectedProduct(product),
            productRepository.find(product.id)
        )
    }

    override fun checkProductsAll() {
        val isChecked = _isCheckedAll.value?.not() ?: true

        cartItems.filterIsInstance<CartViewItem.CartProductItem>().forEachIndexed { index, it ->
            it.product.isChecked = isChecked
            view.showChangedItem(index)
        }

        val products = if (isChecked) { // 전체 선택
            convertItemsToCartProducts(cartItems) - cartSystem.selectedProducts.toSet()
        } else { // 전체 해제
            convertItemsToCartProducts(cartItems).intersect(cartSystem.selectedProducts.toSet())
        }
        var result: CartSystemResult? = _cartSystemResult.value
        products.forEach {
            result = cartSystem.selectProduct(it)
        }
        _cartSystemResult.value = result
        _isCheckedAll.value = isChecked
    }

    override fun checkProduct(product: CartProductModel) {
        _cartSystemResult.value = cartSystem.selectProduct(product.toDomain())
        _isCheckedAll.value = getIsCheckedAll()
    }

    override fun fetchNextPage() {
        val items = cartPagination.nextItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
        _isCheckedAll.value = getIsCheckedAll()
    }

    override fun fetchPrevPage() {
        val items = cartPagination.prevItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
        _isCheckedAll.value = getIsCheckedAll()
    }

    private fun changeListItems(items: List<CartProduct>) {
        val models = convertCartProductToModels(items)
        cartItems.clear()
        cartItems.addAll(models.map { CartViewItem.CartProductItem(it) })
        cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
    }

    override fun updateCartProductCount(id: Int, count: Int) {
        if (count < COUNT_MIN) return
        cartRepository.update(id, count)
        val cartProducts = convertItemsToCartProducts(cartItems)
        cartProducts.find { it.id == id }?.let {
            val index = cartProducts.indexOf(it)
            (cartItems[index] as CartViewItem.CartProductItem).product.count = count
            view.showChangedItem(index)
            _cartSystemResult.value = cartSystem.updateProduct(id, count)
        }
    }

    private fun convertCartProductToModels(cartProducts: List<CartProduct>) =
        cartProducts.map {
            it.toUiModel(cartSystem.isSelectedProduct(it), productRepository.find(it.id))
        }.toMutableList()

    private fun convertItemsToCartProducts(items: List<CartViewItem>): List<CartProduct> =
        items.filterIsInstance<CartViewItem.CartProductItem>().map { it.product.toDomain() }

    companion object {
        private const val PAGINATION_SIZE = 5
        private const val COUNT_MIN = 1
        private const val COUNT_MAX = 100
    }
}
