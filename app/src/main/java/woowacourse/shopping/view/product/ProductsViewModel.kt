package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductsViewModel(
    productsRepository: ProductsRepository = DefaultProductsRepository(),
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private var allCartItems: List<CartItem> = emptyList()

    private val products: MutableLiveData<List<CartItem>> = MutableLiveData()
    val productItems: LiveData<List<ProductsItem>> = products.map { it.toProductItems }

    private val loadable: Boolean get() = allCartItems.size > (products.value?.size ?: 0)
    private val List<CartItem>.toProductItems: List<ProductsItem>
        get() = map(ProductsItem::ProductItem) + ProductsItem.LoadItem(loadable)

    private val _event: MutableSingleLiveData<ProductsEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductsEvent> get() = _event

    init {
        loadAllProducts(productsRepository)
        updateProducts()
    }

    fun updateProducts() {
        runCatching {
            val lastCartItem: CartItem? = products.value?.lastOrNull()
            val startExclusive: Int = allCartItems.indexOf(lastCartItem)
            val lastExclusive: Int =
                (startExclusive + LOAD_PRODUCTS_SIZE).coerceAtMost(allCartItems.size)

            allCartItems.subList(startExclusive + 1, lastExclusive)
        }.onSuccess { newCartItems: List<CartItem> ->
            products.value = products.value?.plus(newCartItems) ?: newCartItems
        }.onFailure {
            _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
        }
    }

    fun updateShoppingCart(onUpdate: () -> Unit) {
        val productItems: List<ProductsItem.ProductItem> =
            productItems.value?.filterIsInstance<ProductsItem.ProductItem>() ?: return

        val cartItemsToUpdate: List<CartItem> =
            productItems
                .map { productItem: ProductsItem.ProductItem ->
                    CartItem(
                        productItem.cartItem.id,
                        productItem.cartItem.name,
                        productItem.cartItem.price,
                        productItem.quantity,
                    )
                }.filter { cartItem -> cartItem.quantity != 0 }

        shoppingCartRepository.update(cartItemsToUpdate) { result: Result<Unit> ->
            result
                .onSuccess {
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
            onUpdate()
        }
    }

    private fun loadAllProducts(productsRepository: ProductsRepository) {
        productsRepository.load { result: Result<List<CartItem>> ->
            result
                .onSuccess { cartItems: List<CartItem> ->
                    allCartItems = cartItems
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
