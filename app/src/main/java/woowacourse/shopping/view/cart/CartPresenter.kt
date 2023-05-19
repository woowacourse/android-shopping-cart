package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.*
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
        cartItems = (models.map { CartViewItem.CartProductItem(it) }
                + CartViewItem.PaginationItem(cartPagination.status)
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
    }

    private fun getIsCheckedAll() =
        cartSystem.selectedProducts.containsAll(convertItemsToCartProduct(cartItems))

    private fun getNextCartProductModel(): CartProductModel? {
        val product = cartPagination.currentLastItem() ?: return null
        return product.toUiModel(
            cartSystem.isSelectedProduct(product),
            productRepository.find(product.id)
        )
    }

    override fun selectAll(isChecked: Boolean) {
        cartItems.filterIsInstance<CartViewItem.CartProductItem>().forEachIndexed { index, it ->
            it.product.isChecked = isChecked
            view.showChangedItem(index)
        }

        val products = if (isChecked) { // 전체 선택
            convertItemsToCartProduct(cartItems) - cartSystem.selectedProducts.toSet()
        } else { // 전체 해제
            convertItemsToCartProduct(cartItems).intersect(cartSystem.selectedProducts.toSet())
        }
        products.forEach {
            _cartSystemResult.value = cartSystem.selectProduct(it)
        }
        _isCheckedAll.value = isChecked
    }

    override fun fetchNextPage() {
        val items = cartPagination.nextItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
    }

    override fun fetchPrevPage() {
        val items = cartPagination.prevItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
    }

    private fun changeListItems(items: List<CartProduct>) {
        val models = convertCartProductToModels(items)
        cartItems.clear()
        cartItems.addAll(models.map { CartViewItem.CartProductItem(it) })
        cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
    }

    override fun updateCartProductCount(id: Int, count: Int) {
        cartRepository.update(id, count)
        val cartProducts = convertItemsToCartProduct(cartItems)
        cartProducts.find { it.id == id }?.let {
            val index = cartProducts.indexOf(it)
            (cartItems[index] as CartViewItem.CartProductItem).product.count = count
            view.showChangedItem(index)
            _cartSystemResult.value = cartSystem.updateProduct(id, count)
        }
    }

    override fun selectProduct(product: CartProductModel) {
        _cartSystemResult.value = cartSystem.selectProduct(product.toDomain())
        _isCheckedAll.value = getIsCheckedAll()
    }

    private fun convertCartProductToModels(cartProducts: List<CartProduct>) =
        cartProducts.map {
            it.toUiModel(cartSystem.isSelectedProduct(it), productRepository.find(it.id))
        }.toMutableList()

    private fun convertItemsToCartProduct(items: List<CartViewItem>): List<CartProduct> =
        items.filterIsInstance<CartViewItem.CartProductItem>().map { it.product.toDomain() }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
