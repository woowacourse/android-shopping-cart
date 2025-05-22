package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _productItems: MutableLiveData<List<ProductsItem>> = MutableLiveData()
    val productItems: LiveData<List<ProductsItem>> get() = _productItems

    private val _event: MutableSingleLiveData<ProductsEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductsEvent> get() = _event

    init {
        loadAllProducts(productsRepository)
    }

    fun updateProducts() {
        runCatching {
            val lastCartItem: CartItem? =
                _productItems.value
                    ?.filterIsInstance<ProductsItem.ProductItem>()
                    ?.lastOrNull()
                    ?.cartItem
            val startExclusive: Int = allCartItems.indexOf(lastCartItem)
            val lastExclusive: Int =
                (startExclusive + LOAD_PRODUCTS_SIZE).coerceAtMost(allCartItems.size)

            allCartItems.subList(startExclusive + 1, lastExclusive)
        }.onSuccess { newCartItems: List<CartItem> ->
            val currentProductItems =
                productItems.value?.filterIsInstance<ProductsItem.ProductItem>() ?: emptyList()
            val productItems = currentProductItems + newCartItems.map(ProductsItem::ProductItem)
            val loadItem = ProductsItem.LoadItem(allCartItems.size > productItems.size)

            _productItems.postValue(productItems + loadItem)
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
                    updateProducts()
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
