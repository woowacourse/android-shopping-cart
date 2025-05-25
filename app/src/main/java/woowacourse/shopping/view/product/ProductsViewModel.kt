package woowacourse.shopping.view.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductsViewModel(
    private val productsRepository: ProductsRepository = DefaultProductsRepository(),
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private var allProducts: List<Product> = emptyList()
    private var shoppingCart: List<CartItem> = emptyList()

    private val _productItems: MutableLiveData<List<ProductsItem>> = MutableLiveData(emptyList())
    val productItems: LiveData<List<ProductsItem>> get() = _productItems

    private val _event: MutableSingleLiveData<ProductsEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductsEvent> get() = _event

    init {
        loadAllProducts()
    }

    fun loadAllProducts() {
        _productItems.value = emptyList()
        productsRepository.load { result: Result<List<Product>> ->
            result
                .onSuccess { products: List<Product> ->
                    allProducts = products
                    loadShoppingCart()
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    private fun loadShoppingCart() {
        shoppingCartRepository.load { result: Result<List<CartItem>> ->
            shoppingCart = result.getOrElse { emptyList() }
            updateProducts()
        }
    }

    fun updateProducts() {
        runCatching {
            val lastProduct: Product? =
                _productItems.value
                    ?.filterIsInstance<ProductsItem.ProductItem>()
                    ?.lastOrNull()
                    ?.product
            val startExclusive: Int = allProducts.indexOf(lastProduct)
            val lastExclusive: Int =
                (startExclusive + LOAD_PRODUCTS_SIZE).coerceAtMost(allProducts.size)
            allProducts.subList(startExclusive + 1, lastExclusive)
        }.onSuccess { newProducts: List<Product> ->
            addProductItems(newProducts)
        }.onFailure {
            _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
        }
    }

    private fun addProductItems(newProducts: List<Product>) {
        val newProductItems: List<ProductsItem.ProductItem> =
            newProducts.map { product: Product ->
                val quantity: Int = shoppingCart.find { it.id == product.id }?.quantity ?: 0
                ProductsItem.ProductItem(product, quantity)
            }

        Log.e("TAG", "addProductItems invoked... newProductItems: $newProductItems")

        val currentProductItems =
            productItems.value?.filterIsInstance<ProductsItem.ProductItem>() ?: emptyList()

        val productItems = currentProductItems + newProductItems
        val loadItem = ProductsItem.LoadItem(allProducts.size > productItems.size)

        _productItems.postValue(productItems + loadItem)
    }

    fun updateShoppingCart(onUpdate: () -> Unit) {
        val productItems: List<ProductsItem.ProductItem> =
            productItems.value?.filterIsInstance<ProductsItem.ProductItem>() ?: return

        val cartItemsToUpdate: List<CartItem> =
            productItems
                .map { productItem: ProductsItem.ProductItem ->
                    CartItem(productItem.product, productItem.quantity)
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

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
