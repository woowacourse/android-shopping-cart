package woowacourse.shopping.domain.repository

import android.util.Log
import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.Product
import java.util.Collections.synchronizedList
import java.util.concurrent.atomic.AtomicInteger

class DefaultShoppingProductRepository(
    private val productsSource: ProductDataSource,
    private val cartSource: ShoppingCartProductIdDataSource,
) : ShoppingProductsRepository {
    override fun loadAllProductsAsyncResult(page: Int, callback: (Result<List<Product>>) -> Unit) {
        productsSource.findByPagedAsyncResult(page) { productsResult ->
            productsResult.map { productData ->
                cartSource.loadAllAsyncResult { cartsResult ->
                    cartsResult.onSuccess { cartsData ->
                        val products = productData.map { productData ->
                            cartsData.find { cartData ->
                                cartData.productId == productData.id
                            }?.let {
                                productData.toDomain(it.quantity)
                            } ?: productData.toDomain(0)
                        }
                        callback(Result.success(products))
                    }
                }
            }
        }
    }

    override fun loadAllProductsAsyncResult2(page: Int, callback: (Result<List<Product>>) -> Unit) {
        productsSource.findAllUntilPageAsyncResult(page) { productsResult ->
            productsResult.map { productData ->
                cartSource.loadAllAsyncResult { cartsResult ->
                    cartsResult.onSuccess { cartsData ->
                        val products = productData.map { productData ->
                            cartsData.find { cartData ->
                                cartData.productId == productData.id
                            }?.let {
                                productData.toDomain(it.quantity)
                            } ?: productData.toDomain(0)
                        }
                        callback(Result.success(products))
                    }
                }
            }
        }
    }

    override fun loadProductsInCartAsyncResult(page: Int, callback: (Result<List<Product>>) -> Unit) {
        cartSource.loadPagedAsyncResult(page) { cartsResult ->
            cartsResult.onSuccess { cartsData ->
                val productsKindCount = cartsData.size
                if (productsKindCount == 0) {
                    callback(Result.success(emptyList()))
                    return@onSuccess
                }

                val products = synchronizedList(List(productsKindCount) { Product.NULL })
                val remaining = AtomicInteger(productsKindCount)

                cartsData.forEachIndexed { index, cartData ->
                    productsSource.findByIdAsyncResult(cartData.productId) { productResult ->
                        productResult
                            .onSuccess { productData ->
                                products[index] = productData.toDomain(cartData.quantity)
                                if (remaining.decrementAndGet() == 0) {
                                    callback(Result.success(products))
                                }
                            }
                            .onFailure {
                                callback(Result.failure(it))
                            }

                    }
                }
            }.onFailure {
                callback(Result.failure(it))
            }
        }
    }

    override fun loadProductAsyncResult(id: Long, callback: (Result<Product>) -> Unit) {
        productsSource.findByIdAsyncResult(id) { productResult ->
            productResult.onSuccess { productData ->
                callback(Result.success(productData.toDomain(quantity = FIRST_QUANTITY)))
            }
                .onFailure { callback(Result.failure(it)) }
        }
    }

    override fun isFinalPageAsyncResult(page: Int, callback: (Result<Boolean>) -> Unit) {
        productsSource.isFinalPageAsyncResult(page, callback)
    }

    override fun isCartFinalPageAsyncResult(page: Int, callback: (Result<Boolean>) -> Unit) {
        cartSource.isFinalPageAsyncResult(page, callback)
    }

    override fun shoppingCartProductQuantityAsyncResult(callback: (Result<Int>) -> Unit) {
        cartSource.loadAllAsyncResult { result ->
            val quantity = result.map { productIdsCountData ->
                productIdsCountData.sumOf { it.quantity }
            }
            callback(quantity)
        }
    }

    override fun increaseShoppingCartProductAsyncResult(id: Long, callback: (Result<Unit>) -> Unit) {
        cartSource.plusProductsIdCountAsyncResult(id) { result ->
            callback(result)
        }
    }

    override fun putItemInCartAsyncResult(id: Long, quantity: Int, callback: (Result<Unit>) -> Unit) {
        cartSource.findByProductIdAsyncResultNonNull(id) { result ->
            result.onSuccess {
                cartSource.plusProductIdCountAsyncResult(id, quantity) {
                    callback(it)
                }
            }
            result.onFailure {
                cartSource.addedNewProductsIdAsyncResult(ProductIdsCountData(id, quantity)) {
                    callback(Result.success(Unit))
                }
            }

        }
    }

    override fun decreaseShoppingCartProductAsyncResult(id: Long, callback: (Result<Unit>) -> Unit) {
        cartSource.findByProductIdAsyncResultNonNull(id) { cartResult ->
            cartResult.onSuccess { cartData ->
                if (cartData.quantity == 1) {
                    cartSource.removedProductsIdAsyncResult(id) {
                        callback(it)
                    }
                } else {
                    cartSource.minusProductsIdCountAsyncResult(id) {
                        callback(it)
                    }
                }
            }
                .onFailure {
                    callback(Result.failure(it))
                }
        }
    }

    override fun addShoppingCartProductAsyncResult(id: Long, callback: (Result<Long>) -> Unit) {
        cartSource.addedNewProductsIdAsyncResult(ProductIdsCountData(id, FIRST_QUANTITY)) {
            callback(it)
        }
    }

    override fun removeShoppingCartProductAsyncResult(id: Long, callback: (Result<Unit>) -> Unit) {
        cartSource.removedProductsIdAsyncResult(id) {
            Log.d(TAG, "removeShoppingCartProductAsyncResult: $it")
            callback(it)
        }
    }

    companion object {
        private const val FIRST_QUANTITY = 1
        private const val TAG = "DefaultShoppingProductRepository"
    }
}
