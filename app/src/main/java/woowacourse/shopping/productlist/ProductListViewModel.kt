package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productlist.LoadProductState.ShowProducts

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _loadState: MutableLiveData<LoadProductState> = MutableLiveData()
    val loadState: LiveData<LoadProductState> get() = _loadState

    val totalSize: Int = productRepository.productsTotalSize()

    val totalCartItemCount = _loadState.map { it.currentProducts.totalCartItemCount() }

    private fun currentLoadState(): ProductUiModels = _loadState.value?.currentProducts ?: ProductUiModels.default()

    fun loadProducts() {
        runCatching {
            val products =
                productRepository.products(
                    currentLoadState().totalProductCount(),
                    PRODUCTS_OFFSET_SIZE,
                )
            val cartItems = shoppingRepository.shoppingCart().items
            products.map { product ->
                product.toProductUiModel(productOfCartQuantity(cartItems, product))
            }.let(::ProductUiModels)
        }.onSuccess { newProducts ->
            _loadState.value =
                ShowProducts(newProducts, currentLoadState().addProduct(newProducts.products))
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
            val cartItem = ShoppingCartItem(productRepository.productById(productId))
            val shoppingCart = shoppingRepository.shoppingCart()
            shoppingRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
        }.onSuccess {
            val addedProduct = shoppingRepository.cartItemByProductId(productId).toProductUiModel()
            _loadState.value =
                LoadProductState.ChangeItemCount(
                    addedProduct,
                    currentLoadState().updateProduct(addedProduct),
                )
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun minusProductCount(productId: Long) {
        runCatching {
            shoppingRepository.decreasedCartItem(productId)
        }.onSuccess { result ->
            when (result) {
                is QuantityUpdate.Success -> {
                    val updatedProduct = result.value.toProductUiModel()
                    _loadState.value =
                        LoadProductState.ChangeItemCount(
                            updatedProduct,
                            currentLoadState().updateProduct(updatedProduct),
                        )
                }

                QuantityUpdate.Failure ->
                    deleteShoppingCart(productId)
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun deleteShoppingCart(productId: Long) {
        runCatching {
            shoppingRepository.deleteShoppingCartItem(productId)
            productRepository.productById(productId).toProductUiModel(NO_PRODUCT_OF_CART_ITEM)
        }.onSuccess { updatedProduct ->
            val updatedProductList = currentLoadState().updateProduct(updatedProduct)
            _loadState.value =
                LoadProductState.DeleteProductFromCart(updatedProductList, updatedProduct)
        }
    }

    fun plusProductCount(productId: Long) {
        runCatching {
            shoppingRepository.increasedCartItem(productId)
        }.onSuccess { result ->
            when (result) {
                is QuantityUpdate.Success -> {
                    val updatedUiModel = result.value.toProductUiModel()
                    _loadState.value =
                        LoadProductState.ChangeItemCount(
                            updatedUiModel,
                            currentLoadState().updateProduct(updatedUiModel),
                        )
                }

                QuantityUpdate.Failure ->
                    _loadState.value =
                        LoadProductState.PlusFail(currentLoadState())
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun reloadProductOfInfo(productId: Long)  {
        runCatching {
            shoppingRepository.cartItemByProductId(productId)
        }.onSuccess { cartItem ->
            val updatedProduct = cartItem.toProductUiModel()
            _loadState.value = LoadProductState.ChangeItemCount(updatedProduct, currentLoadState().updateProduct(updatedProduct))
        }
    }

    companion object {
        private const val PRODUCTS_OFFSET_SIZE = 20
        private const val NO_PRODUCT_OF_CART_ITEM = 0
    }
}
