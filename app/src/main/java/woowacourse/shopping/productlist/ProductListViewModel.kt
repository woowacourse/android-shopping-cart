package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productlist.uimodel.LoadProductState
import woowacourse.shopping.productlist.uimodel.LoadProductState.ShowProducts
import woowacourse.shopping.productlist.uimodel.ProductUiModel
import woowacourse.shopping.productlist.uimodel.RecentProductUiModel
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _loadState: MutableLiveData<LoadProductState> = MutableLiveData()
    val loadState: LiveData<LoadProductState> get() = _loadState

    val totalSize: Int = productRepository.productsTotalSize()

    val totalCartItemCount =
        _loadState.map {
            it.currentProducts.filterNot { product -> product.cartItemCount == 0 }
                .sumOf { product -> product.cartItemCount }
        }

    private val _recentProducts: MutableLiveData<List<RecentProductUiModel>> = MutableLiveData()
    val recentProducts: LiveData<List<RecentProductUiModel>> get() = _recentProducts

    private fun currentLoadState(): List<ProductUiModel> = _loadState.value?.currentProducts ?: emptyList()

    fun initProducts() {
        _loadState.value ?: loadProducts()
    }

    fun loadMoreProducts() {
        loadProducts()
    }

    private fun loadProducts() {
        runCatching {
            val products =
                productRepository.products(
                    currentLoadState().size,
                    PRODUCTS_OFFSET_SIZE,
                )
            val cartItems = shoppingRepository.shoppingCart().items
            products.map { product ->
                product.toProductUiModel(productOfCartQuantity(cartItems, product))
            }
        }.onSuccess { newProducts ->
            _loadState.value =
                ShowProducts(
                    newProducts,
                    currentLoadState().addProduct(newProducts),
                )
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun productOfCartQuantity(
        cartItems: List<ShoppingCartItem>,
        product: Product,
    ): Int =
        cartItems.firstOrNull { it.product.id == product.id }?.totalQuantity
            ?: NO_PRODUCT_OF_CART_ITEM

    fun addProductToCart(productId: Long) {
        runCatching {
            val product = productRepository.productById(productId)
            shoppingRepository.addCartItem(ShoppingCartItem(product))
        }.onSuccess {
            val addedProduct = shoppingRepository.cartItemByProductId(productId).toProductUiModel()
            changeItemCount(addedProduct)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun deleteShoppingCart(productId: Long) {
        runCatching {
            shoppingRepository.deleteShoppingCartItem(productId)
        }.onSuccess {
            val updatedProduct =
                productRepository.productById(productId).toProductUiModel(NO_PRODUCT_OF_CART_ITEM)
            changeItemCount(updatedProduct)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun minusProductCount(productId: Long) {
        runCatching {
            shoppingRepository.decreasedCartItem(productId)
        }.onSuccess { result ->
            when (result) {
                is QuantityUpdate.Success -> changeItemCount(result.value.toProductUiModel())
                QuantityUpdate.Failure ->
                    deleteShoppingCart(productId)
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun plusProductCount(productId: Long) {
        runCatching {
            shoppingRepository.increasedCartItem(productId)
        }.onSuccess { result ->
            when (result) {
                is QuantityUpdate.Success -> changeItemCount(result.value.toProductUiModel())
                QuantityUpdate.Failure ->
                    _loadState.value =
                        LoadProductState.PlusFail(currentLoadState())
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun changeItemCount(updatedProduct: ProductUiModel) {
        _loadState.value =
            LoadProductState.ChangeItemCount(
                updatedProduct.let(::listOf),
                currentLoadState().updateProduct(updatedProduct),
            )
    }

    fun reloadProductOfInfo(productIds: List<Long>) {
        runCatching {
            val cartItems = shoppingRepository.cartItemsByProductIds(productIds.toList())
            val products = productIds.map { productRepository.productById(it) }

            products.map { product ->
                product.toProductUiModel(productOfCartQuantity(cartItems, product))
            }
        }.onSuccess { updatedProducts ->
            _loadState.value =
                LoadProductState.ChangeItemCount(
                    updatedProducts,
                    currentLoadState().updateProducts(updatedProducts),
                )
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun loadRecentProduct() {
        runCatching {
            productRepository.sortedRecentProduct()
        }.onSuccess { recentProduct ->
            _recentProducts.value = recentProduct.map { it.toRecentProductUiModel() }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun List<ProductUiModel>.updateProducts(updatedProducts: List<ProductUiModel>): List<ProductUiModel> =
        currentLoadState().map { product ->
            if (updatedProducts.map { it.id }.contains(product.id)) {
                updatedProducts.first { product.id == it.id }
            } else {
                product
            }
        }

    private fun List<ProductUiModel>.updateProduct(product: ProductUiModel): List<ProductUiModel> =
        currentLoadState().map { if (it.id == product.id) product else it }

    private fun List<ProductUiModel>.addProduct(product: List<ProductUiModel>): List<ProductUiModel> = currentLoadState() + product

    companion object {
        private const val PRODUCTS_OFFSET_SIZE = 20
        private const val NO_PRODUCT_OF_CART_ITEM = 0
    }
}
