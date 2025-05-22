package woowacourse.shopping.data.repository

import android.util.Log
import woowacourse.shopping.data.CartMapper.toEntity
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun getCartItemCount(onResult: (Result<Int?>) -> Unit) {
        runCatching {
            cartDataSource.getCartProductCount { result ->
                onResult(result)
            }
        }.onFailure { e ->
            onResult(Result.failure(e))
        }
    }

    override fun getTotalQuantity(onResult: (Result<Int?>) -> Unit) {
        runCatching {
            cartDataSource.getTotalQuantity { result ->
                onResult(result)
            }
        }.onFailure { e ->
            onResult(Result.failure(e))
        }
    }

    override fun getCartItemById(
        productId: Long,
        onResult: (Result<CartEntity?>) -> Unit,
    ) {
        runCatching {
            cartDataSource.getCartItemById(productId) { result ->
                onResult(result)
            }
        }.onFailure { e ->
            onResult(Result.failure(e))
        }
    }

    override fun insertProduct(
        cartItem: CartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatching {
            cartDataSource.insertProduct(cartItem.toEntity()) { result ->
                onResult(result)
            }
        }.onFailure { e ->
            onResult(Result.failure(e))
        }
    }

    override fun insertOrIncrease(
        product: Product,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        cartDataSource.existsByProductId(product.productId) { existsResult ->
            existsResult
                .onSuccess { exists ->
                    if (exists) {
                        cartDataSource.increaseQuantity(product.productId, quantity) { result ->
                            onResult(result)
                        }
                    } else {
                        cartDataSource.insertProduct(product.toEntity(quantity)) { result ->
                            onResult(result)
                        }
                    }
                }.onFailure { e ->
                    onResult(Result.failure(e))
                }
        }
    }

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatching {
            cartDataSource.increaseQuantity(productId, quantity) { result ->
                onResult(result)
            }
        }.onFailure { e ->
            Log.e("CartRepository", "increaseQuantity failed", e)
            onResult(Result.failure(e))
        }
    }

    override fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatching {
            cartDataSource.decreaseQuantity(productId) { result ->
                onResult(result)
            }
        }.onFailure { e ->
            Log.e("CartRepository", "decreaseQuantity failed", e)
        }
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatching {
            cartDataSource.deleteProductById(productId) { result ->
                onResult(result)
            }
        }.onFailure { e ->
            Log.e("CartRepository", "delete failed", e)
        }
    }
}
