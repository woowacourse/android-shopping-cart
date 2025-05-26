package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toCartItem
import woowacourse.shopping.presentation.model.toProduct
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem

class CatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _items = MutableLiveData<List<CatalogItem>>()
    val items: LiveData<List<CatalogItem>> = _items

    private val _itemUpdateEvent = MutableLiveData<ProductUiModel>()
    val itemUpdateEvent: LiveData<ProductUiModel> = _itemUpdateEvent

    private val _totalCartCount = MutableLiveData<Int>()
    val totalCartCount: LiveData<Int> = _totalCartCount

    private val loadSize: Int = 20
    private var lastId: Long = DEFAULT_ID

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        productRepository.loadCartItems { cartItems ->

            val totalCount = cartItems?.sumOf { it.amount } ?: 0
            _totalCartCount.postValue(totalCount)

            val cartItemState =
                cartItems?.associateBy(
                    { it.product.id },
                    { it.toUiModel() },
                )

            productRepository.loadRecentProducts(limit = 10) { recentProducts ->
                val recentUiModels = recentProducts.map { it.toUiModel() }
                val recentItem = CatalogItem.RecentProductItem(recentUiModels)

                productRepository.loadProducts(lastId, loadSize) { fetchedProducts, hasMore ->

                    val fetchedUiModels =
                        fetchedProducts.map { product ->
                            cartItemState?.get(product.id) ?: product.toUiModel()
                        }

                    lastId = fetchedUiModels.lastOrNull()?.id ?: DEFAULT_ID

                    val currentUiModels =
                        _items.value
                            .orEmpty()
                            .filterIsInstance<CatalogItem.ProductItem>()
                            .map { it.product }

                    val combinedUiModels =
                        (currentUiModels + fetchedUiModels)
                            .distinctBy { it.id }

                    val updatedItems = mutableListOf<CatalogItem>()

                    if (recentUiModels.isNotEmpty()) {
                        updatedItems.add(recentItem)
                    }

                    updatedItems.addAll(
                        combinedUiModels.map { CatalogItem.ProductItem(it) },
                    )

                    if (hasMore && updatedItems.none { it is CatalogItem.LoadMoreItem }) {
                        updatedItems.add(CatalogItem.LoadMoreItem)
                    }

                    _items.postValue(updatedItems)
                }
            }
        }
    }

    fun refreshCartState() {
        if (_items.value.isNullOrEmpty()) return

        productRepository.loadCartItems { cartItems ->
            val updatedCartState =
                cartItems?.associateBy(
                    { it.product.id },
                    { it.toUiModel() },
                )

            val totalCount = cartItems?.sumOf { it.amount } ?: 0
            _totalCartCount.postValue(totalCount)

            val updatedItems =
                _items.value
                    ?.map {
                        if (it is CatalogItem.ProductItem) {
                            val updatedProduct = updatedCartState?.get(it.product.id)
                            CatalogItem.ProductItem(
                                updatedProduct ?: it.product.copy(amount = 0),
                            )
                        } else {
                            it
                        }
                    }

            _items.postValue(updatedItems ?: emptyList())
        }
    }

    fun initialAddToCart(product: ProductUiModel) {
        val updatedProduct = product.copy(amount = 1)
        cartRepository.addCartItem(updatedProduct.toCartItem()) {
            _itemUpdateEvent.postValue(updatedProduct)
            calculateTotalCartCount()
        }
    }

    fun increaseCartItem(productId: Long) {
        cartRepository.increaseCartItem(productId) { updatedCartItem ->
            handleUpdatedCartItem(updatedCartItem)
        }
    }

    fun decreaseCartItem(productId: Long) {
        cartRepository.decreaseCartItem(productId) { updatedCartItem ->
            handleUpdatedCartItem(updatedCartItem)
        }
    }

    fun addRecentProduct(product: ProductUiModel) {
        productRepository.addRecentProduct(product.toProduct())
        updateRecentProducts()
    }

    private fun handleUpdatedCartItem(updatedCartItem: CartItem?) {
        updatedCartItem?.let {
            _itemUpdateEvent.postValue(it.toUiModel())
            calculateTotalCartCount()
        }
    }

    private fun calculateTotalCartCount() {
        productRepository.loadCartItems { cartItems ->
            val totalCount = cartItems?.sumOf { it.amount } ?: 0
            _totalCartCount.postValue(totalCount)
        }
    }

    private fun updateRecentProducts() {
        productRepository.loadRecentProducts(limit = 10) { recentProducts ->
            val recentUiModels = recentProducts.map { it.toUiModel() }
            val recentItem = CatalogItem.RecentProductItem(recentUiModels)

            val currentItems = _items.value.orEmpty()

            val updatedItems =
                buildList {
                    if (recentUiModels.isNotEmpty()) {
                        add(recentItem)
                    }

                    addAll(
                        currentItems.filter { it !is CatalogItem.RecentProductItem },
                    )
                }

            _items.postValue(updatedItems)
        }
    }

    companion object {
        private const val DEFAULT_ID = 0L

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val productRepository = RepositoryProvider.productRepository
                    val cartRepository = RepositoryProvider.cartRepository
                    return CatalogViewModel(productRepository, cartRepository) as T
                }
            }
    }
}
