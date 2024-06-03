package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productlist.uimodel.ProductChangeEvent
import woowacourse.shopping.productlist.uimodel.ProductUiModel
import woowacourse.shopping.productlist.uimodel.ProductUiState
import woowacourse.shopping.productlist.uimodel.RecentProductUiModel
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingRepository
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _productChangeEvent: MutableSingleLiveData<ProductChangeEvent> =
        MutableSingleLiveData()
    val productChangeEvent: SingleLiveData<ProductChangeEvent> get() = _productChangeEvent

    private val _productUiState: MutableLiveData<ProductUiState> = MutableLiveData()
    val productUiState: LiveData<ProductUiState> get() = _productUiState

    val totalSize: Int = productRepository.productsTotalSize()

    val totalCartItemCount =
        _productUiState.map {
            it.currentProducts.filterNot { product -> product.cartItemCount == 0 }
                .sumOf { product -> product.cartItemCount }
        }

    private val _recentProducts: MutableLiveData<List<RecentProductUiModel>> = MutableLiveData()
    val recentProducts: LiveData<List<RecentProductUiModel>> get() = _recentProducts

    private fun currentProducts(): List<ProductUiModel> = _productUiState?.value?.currentProducts ?: emptyList()

    fun initProducts() {
        _productUiState.value?.let {
            _productUiState.value = ProductUiState.Init(currentProducts())
            return
        }

        runCatching {
            val products =
                productRepository.products(
                    NO_PRODUCT_OF_CART_ITEM,
                    PRODUCTS_OFFSET_SIZE,
                )
            val cartItems = shoppingRepository.shoppingCart().items
            products.map { product ->
                product.toProductUiModel(productOfCartQuantity(cartItems, product))
            }
        }.onSuccess { newProducts ->
            _productUiState.value = ProductUiState.Init(newProducts)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun loadMoreProducts() {
        runCatching {
            val products =
                productRepository.products(
                    currentProducts().size,
                    PRODUCTS_OFFSET_SIZE,
                )
            val cartItems = shoppingRepository.shoppingCart().items
            products.map { product ->
                product.toProductUiModel(productOfCartQuantity(cartItems, product))
            }
        }.onSuccess { newProducts ->
            _productUiState.value = ProductUiState.Change(currentProducts() + newProducts)
            _productChangeEvent.setValue(ProductChangeEvent.AddProducts(newProducts))
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
                QuantityUpdate.CantChange ->
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
                QuantityUpdate.CantChange ->
                    _productChangeEvent.setValue(ProductChangeEvent.PlusFail)
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun changeItemCount(updatedProduct: ProductUiModel) {
        _productChangeEvent.setValue(
            ProductChangeEvent.ChangeItemCount(
                updatedProduct.let(::listOf),
            ),
        )
        _productUiState.value =
            ProductUiState.Change(currentProducts().updateProduct(updatedProduct))
    }

    fun reloadProductOfInfo(productIds: List<Long>) {
        runCatching {
            val cartItems = shoppingRepository.cartItemsByProductIds(productIds.toList())
            val products =
                productIds.filterNot { productId ->
                    cartItems.map { it.product.id }.contains(productId)
                }.map { productRepository.productById(it) }

            cartItems.map { it.toProductUiModel() } + products.map { it.toProductUiModel(0) }
        }.onSuccess { updatedProducts ->
            _productChangeEvent.setValue(
                ProductChangeEvent.ChangeItemCount(
                    updatedProducts,
                ),
            )
            _productUiState.value =
                ProductUiState.Change(currentProducts().updateProducts(updatedProducts))
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
        currentProducts().map { product ->
            if (updatedProducts.map { it.id }.contains(product.id)) {
                updatedProducts.first { product.id == it.id }
            } else {
                product
            }
        }

    private fun List<ProductUiModel>.updateProduct(product: ProductUiModel): List<ProductUiModel> =
        currentProducts().map { if (it.id == product.id) product else it }

    companion object {
        private const val PRODUCTS_OFFSET_SIZE = 20
        private const val NO_PRODUCT_OF_CART_ITEM = 0
    }
}
