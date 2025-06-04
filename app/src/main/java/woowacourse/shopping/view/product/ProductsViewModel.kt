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
import woowacourse.shopping.view.product.ProductsItem.RecentWatchingItem

class ProductsViewModel(
    private val productsRepository: ProductsRepository = DefaultProductsRepository.get(),
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
        val currentProducts: List<ProductsItem> = _products.value ?: emptyList()
        val lastProductId: Long? =
            (currentProducts.lastOrNull { it is ProductItem } as? ProductItem)?.product?.id

        productsRepository.load(lastProductId, LOAD_PRODUCTS_SIZE + 1) { result ->
            result
                .onSuccess { newProducts ->
                    loadable = newProducts.size == LOAD_PRODUCTS_SIZE + 1
                    val productsToShow = newProducts.take(LOAD_PRODUCTS_SIZE)

                    updateProductsShoppingCartQuantity(productsToShow, currentProducts)
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    private fun updateProductsShoppingCartQuantity(
        productsToShow: List<Product>,
        currentProducts: List<ProductsItem>,
    ) {
        shoppingCartRepository.fetchSelectedQuantity(productsToShow) { result ->
            result
                .onSuccess { shoppingCartProducts: List<ShoppingCartProduct> ->
                    handleShoppingCartQuantitySuccess(
                        productsToShow,
                        currentProducts,
                        shoppingCartProducts,
                    )
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    private fun handleShoppingCartQuantitySuccess(
        productsToShow: List<Product>,
        currentProducts: List<ProductsItem>,
        shoppingCartProducts: List<ShoppingCartProduct>,
    ) {
        val productsWithoutLoadItem = currentProducts.filterNot { it.viewType == LoadItem.viewType }

        val updatedProductsShoppingCartQuantity =
            productsToShow.map { product ->
                val quantity = shoppingCartProducts.find { it.product == product }?.quantity ?: 0
                ProductItem(product, quantity)
            }
        updateRecentProducts(
            productsWithoutLoadItem,
            updatedProductsShoppingCartQuantity,
        )
    }

    private fun updateRecentProducts(
        currentProductsItemWithoutLoadItem: List<ProductsItem>,
        updatedProductItems: List<ProductItem>,
    ) {
        val currentProducts: List<ProductsItem> =
            currentProductsItemWithoutLoadItem.filterNot { it is RecentWatchingItem }

        productsRepository.getRecentWatchingProducts(10) { result ->
            result
                .onSuccess { recentWatchingProducts: List<Product> ->
                    updateNewProducts(
                        recentWatchingProducts = recentWatchingProducts,
                        currentProducts = currentProducts,
                        updatedProductItems = updatedProductItems,
                    )
                }.onFailure {
                    updateNewProducts(
                        recentWatchingProducts = null,
                        currentProducts = currentProducts,
                        updatedProductItems = updatedProductItems,
                    )
                    _event.postValue(ProductsEvent.UPDATE_RECENT_WATCHING_PRODUCTS_FAILURE)
                }
        }
    }

    private fun updateNewProducts(
        recentWatchingProducts: List<Product>?,
        currentProducts: List<ProductsItem>,
        updatedProductItems: List<ProductItem>,
    ) {
        if (recentWatchingProducts == null || recentWatchingProducts.isEmpty()) {
            _products.postValue(
                buildList {
                    addAll(currentProducts)
                    addAll(updatedProductItems)
                    if (loadable) add(LoadItem)
                },
            )
            return
        }
        _products.postValue(
            buildList {
                add(RecentWatchingItem(recentWatchingProducts))
                addAll(currentProducts)
                addAll(updatedProductItems)
                if (loadable) add(LoadItem)
            },
        )
    }

    private fun updateShoppingCartQuantity() {
        shoppingCartRepository.fetchAllQuantity { result ->
            result
                .onSuccess { quantity: Int ->
                    _shoppingCartQuantity.postValue(quantity)
                }
        }
    }

    fun updateRecentWatching() {
        productsRepository.getRecentWatchingProducts(10) { result ->
            result
                .onSuccess { recentWatchingProducts: List<Product> ->
                    updateRecentWatchingProducts(recentWatchingProducts)
                }.onFailure {
                    _event.postValue(ProductsEvent.UPDATE_RECENT_WATCHING_PRODUCTS_FAILURE)
                }
        }
    }

    private fun updateRecentWatchingProducts(recentWatchingProducts: List<Product>) {
        val recentWatchingItem =
            if (recentWatchingProducts.isEmpty()) {
                null
            } else {
                RecentWatchingItem(recentWatchingProducts)
            }

        val currentProducts = _products.value ?: return
        val withoutOldRecentWatching =
            currentProducts.filterNot { it is RecentWatchingItem }

        val updatedProducts =
            buildList {
                recentWatchingItem?.let { add(it) }
                addAll(withoutOldRecentWatching)
            }

        _products.postValue(updatedProducts)
    }

    fun addProductToShoppingCart(product: Product) {
        shoppingCartRepository.add(product, 1) { result ->
            result
                .onSuccess {
                    updateShoppingCartPlusQuantity(product)
                }.onFailure {
                    _event.postValue(ProductsEvent.NOT_ADD_TO_SHOPPING_CART)
                }
        }
    }

    private fun updateShoppingCartPlusQuantity(product: Product) {
        val currentProducts = products.value?.toMutableList() ?: return
        val targetIndex: Int =
            currentProducts.indexOfFirst { it is ProductItem && it.product == product }

        if (targetIndex != -1) {
            val productItem = currentProducts[targetIndex] as ProductItem
            val updatedItem =
                productItem.copy(selectedQuantity = productItem.selectedQuantity + 1)
            currentProducts[targetIndex] = updatedItem
            _products.postValue(currentProducts)
        }
        _shoppingCartQuantity.postValue(shoppingCartQuantity.value?.plus(1))
    }

    fun minusProductToShoppingCart(product: Product) {
        shoppingCartRepository.decreaseQuantity(product) { result ->
            result
                .onSuccess {
                    updateShoppingCartMinusQuantity(product)
                }.onFailure {
                    _event.postValue(ProductsEvent.NOT_MINUS_TO_SHOPPING_CART)
                }
        }
    }

    private fun updateShoppingCartMinusQuantity(product: Product) {
        val currentProducts = products.value?.toMutableList() ?: return
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
    }

    fun fetchSelectedQuantity(product: Product) {
        shoppingCartRepository.fetchSelectedQuantity(product) { result ->
            result
                .onSuccess { selectedQuantity: Int? ->
                    updateSelectedQuantity(selectedQuantity, product)
                }
        }
    }

    private fun updateSelectedQuantity(
        selectedQuantity: Int?,
        product: Product,
    ) {
        if (selectedQuantity == null) return
        val currentList = products.value.orEmpty()
        val updatedList =
            currentList.map { item: ProductsItem ->
                if (item is ProductItem && item.product.id == product.id) {
                    item.copy(selectedQuantity = selectedQuantity)
                } else {
                    item
                }
            }
        _products.postValue(updatedList)
        updateShoppingCartQuantity()
    }

    fun fetchSelectedQuantity(products: List<Product>) {
        shoppingCartRepository.fetchSelectedQuantity(products) { result ->
            result
                .onSuccess { fetchedList: List<ShoppingCartProduct> ->
                    updateSelectedQuantity(fetchedList)
                }
        }
    }

    private fun updateSelectedQuantity(fetchedList: List<ShoppingCartProduct>) {
        val updatedProducts =
            products.value.orEmpty().map { item ->
                if (item is ProductItem) {
                    getUpdatedProductQuantity(fetchedList, item)
                } else {
                    item
                }
            }
        _products.postValue(updatedProducts)
        updateShoppingCartQuantity()
    }

    private fun getUpdatedProductQuantity(
        fetchedList: List<ShoppingCartProduct>,
        item: ProductItem,
    ): ProductItem {
        val matchingItem: ShoppingCartProduct? = fetchedList.find { it.product == item.product }
        return if (matchingItem != null) {
            item.copy(selectedQuantity = matchingItem.quantity)
        } else {
            item
        }
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
