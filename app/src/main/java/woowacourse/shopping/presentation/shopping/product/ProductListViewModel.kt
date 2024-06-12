package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.cart.toUiModel
import woowacourse.shopping.presentation.shopping.toShoppingUiModel
import woowacourse.shopping.presentation.util.SingleLiveEvent

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ProductAction {
    private val _products = MutableLiveData<List<ShoppingUiModel>>(emptyList())
    val products: LiveData<List<ShoppingUiModel>> = _products

    private val _clickedItemId = SingleLiveEvent<Long>()
    val clickedItemId: LiveData<Long> = _clickedItemId

    private var currentPage = 0

    fun loadProducts() {
        val currentProducts =
            _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()

        var loadProducts: List<ShoppingUiModel.Product> =
            currentProducts +
                    shoppingRepository.products(currentPage++, PRODUCT_AMOUNT)
                        .map { it.toShoppingUiModel(false) }

        val carProducts: List<ShoppingUiModel.Product> =
            cartRepository.totalCartProducts().map { it.toUiModel(false).product }

        val cartIds = carProducts.map { it.id }
        loadProducts =
            loadProducts.map { product ->
                if (product.id in cartIds) {
                    carProducts.find { product.id == it.id } ?: product
                } else {
                    product
                }
            }

        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.postValue(loadProducts + ShoppingUiModel.LoadMore)
        } else {
            _products.postValue(loadProducts)
        }
    }

    override fun onClickItem(id: Long) {
        _clickedItemId.value = id
    }

    override fun moreItems() {
        loadProducts()
    }

    override fun onPlus(cartProduct: ShoppingUiModel.Product) {
        val currentProducts = _products.value?.filterIsInstance<ShoppingUiModel.Product>() ?: return
        val newProducts =
            currentProducts.map {
                if (it.id == cartProduct.id) {
                    val newCount = it.count + 1
                    val visible = newCount > 0
                    cartRepository.addCartProduct(it.id, newCount)
                    it.copy(count = newCount, isVisible = visible)
                } else {
                    it
                }
            }
        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = newProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = newProducts
        }
    }

    override fun onMinus(cartProduct: ShoppingUiModel.Product) {
        val currentProducts = _products.value?.filterIsInstance<ShoppingUiModel.Product>() ?: return
        val newProducts =
            currentProducts.map {
                if (it.id == cartProduct.id) {
                    val newCount = it.count - 1
                    val visible = newCount > 0
                    if (newCount == 0) {
                        cartRepository.deleteCartProduct(it.id)
                    } else {
                        cartRepository.addCartProduct(it.id, newCount)
                    }
                    it.copy(count = newCount, isVisible = visible)
                } else {
                    it
                }
            }
        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = newProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = newProducts
        }
    }

    companion object {
        private const val PRODUCT_AMOUNT: Int = 20

        fun factory(
            shopping: ShoppingRepository,
            cart: CartRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(shopping, cart) }
        }
    }
}
