package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct
import woowacourse.shopping.view.common.MutableSingleLiveData
import woowacourse.shopping.view.common.SingleLiveData
import woowacourse.shopping.view.product.ProductsItem.LoadItem
import woowacourse.shopping.view.product.ProductsItem.ProductItem

class ProductsViewModel(
    private val productsRepository: ProductsRepository = DefaultProductsRepository(),
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository.get(),
) : ViewModel() {
    private val _products: MutableLiveData<List<ProductsItem>> = MutableLiveData(emptyList())
    val products: LiveData<List<ProductsItem>> get() = _products

    private val _event: MutableSingleLiveData<ProductsEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductsEvent> get() = _event

    private val _shoppingCartQuantity: MutableLiveData<Int> = MutableLiveData(0)
    val shoppingCartQuantity: LiveData<Int> get() = _shoppingCartQuantity

    private var loadable: Boolean = false

    init {
        updateProducts()
        updateShoppingCartQuantity()
    }

    fun updateProducts() {
        val currentProducts: List<ProductsItem> = products.value ?: emptyList()
        val lastProductId: Long? =
            (currentProducts.lastOrNull { it is ProductItem } as? ProductItem)?.product?.id

        productsRepository.load(lastProductId, LOAD_PRODUCTS_SIZE + 1) { result ->
            result
                .onSuccess { newProducts ->
                    loadable = newProducts.size == LOAD_PRODUCTS_SIZE + 1
                    val productsToShow = newProducts.take(LOAD_PRODUCTS_SIZE)

                    updateProductsQuantity(productsToShow, currentProducts)
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    private fun updateProductsQuantity(
        productsToShow: List<Product>,
        currentProducts: List<ProductsItem>,
    ) {
        shoppingCartRepository.fetchSelectedQuantity(productsToShow) { result ->
            result
                .onSuccess { shoppingCartProducts: List<ShoppingCartProduct> ->
                    val productsWithoutLoadItem =
                        if (currentProducts.lastOrNull() is LoadItem) {
                            currentProducts.dropLast(1)
                        } else {
                            currentProducts
                        }
                    val updatedProductItems =
                        productsToShow.map { product ->
                            val quantity =
                                shoppingCartProducts.find { it.product == product }?.quantity ?: 0
                            ProductItem(product, quantity)
                        }

                    val updatedList =
                        productsWithoutLoadItem + updatedProductItems +
                            if (loadable) listOf(LoadItem) else emptyList()

                    _products.postValue(updatedList)
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    fun addProductToShoppingCart(product: Product) {
        shoppingCartRepository.add(product, 1) { result ->
            result
                .onSuccess {
                    val currentProducts = products.value?.toMutableList() ?: return@add
                    val index: Int =
                        currentProducts.indexOfFirst { it is ProductItem && it.product == product }

                    if (index != -1) {
                        val productItem = currentProducts[index] as ProductItem
                        val updatedItem =
                            productItem.copy(selectedQuantity = productItem.selectedQuantity + 1)
                        currentProducts[index] = updatedItem
                        _products.postValue(currentProducts)
                    }
                    _shoppingCartQuantity.postValue(shoppingCartQuantity.value?.plus(1))
                }.onFailure {
                    _event.postValue(ProductsEvent.NOT_ADD_TO_SHOPPING_CART)
                }
        }
    }

    fun minusProductToShoppingCart(product: Product) {
        shoppingCartRepository.decreaseQuantity(product) { result ->
            result
                .onSuccess {
                    val currentProducts = products.value?.toMutableList() ?: return@decreaseQuantity
                    val index: Int =
                        currentProducts.indexOfFirst { it is ProductItem && it.product == product }

                    if (index != -1) {
                        val productItem = currentProducts[index] as ProductItem
                        val updatedItem =
                            productItem.copy(selectedQuantity = productItem.selectedQuantity - 1)
                        currentProducts[index] = updatedItem
                        _products.postValue(currentProducts)
                    }
                    _shoppingCartQuantity.postValue(shoppingCartQuantity.value?.minus(1))
                }.onFailure {
                    _event.postValue(ProductsEvent.NOT_MINUS_TO_SHOPPING_CART)
                }
        }
    }

    fun updateSelectedQuantity(product: Product) {
        shoppingCartRepository.fetchSelectedQuantity(product) { result ->
            result
                .onSuccess { selectedQuantity: Int? ->
                    if (selectedQuantity == null) return@fetchSelectedQuantity
                    val currentList = products.value.orEmpty()
                    val updatedList =
                        currentList.map { item ->
                            if (item is ProductItem && item.product == product) {
                                item.copy(selectedQuantity = selectedQuantity)
                            } else {
                                item
                            }
                        }
                    _products.postValue(updatedList)
                    updateShoppingCartQuantity()
                }
        }
    }

    fun updateSelectedQuantity(products: List<Product>) {
        shoppingCartRepository.fetchSelectedQuantity(products) { result ->
            result
                .onSuccess { fetchedList: List<ShoppingCartProduct> ->
                    val updated =
                        this.products.value.orEmpty().map { item ->
                            if (item is ProductItem) {
                                val match = fetchedList.find { it.product == item.product }
                                if (match != null) {
                                    item.copy(selectedQuantity = match.quantity)
                                } else {
                                    item
                                }
                            } else {
                                item
                            }
                        }
                    _products.postValue(updated)
                    updateShoppingCartQuantity()
                }
        }
    }

    private fun updateShoppingCartQuantity() {
        shoppingCartRepository.fetchAllQuantity { result ->
            result
                .onSuccess { quantity: Int ->
                    _shoppingCartQuantity.postValue(quantity)
                }
        }
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
