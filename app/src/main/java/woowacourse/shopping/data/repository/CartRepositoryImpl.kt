package woowacourse.shopping.data.repository

import android.util.Log
import woowacourse.shopping.data.CartMapper.toEntity
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addToCart(item: CartItem) {
        TODO("Not yet implemented")
    }

    override fun removeFromCart(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun existsByProductId(
        productId: Long,
        onResult: (Result<Boolean>) -> Unit,
    ) {
        runCatching {
            cartDataSource.existsByProductId(productId) { result ->
                onResult(result)
            }
        }
    }

    override fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
//        TODO("Not yet implemented")
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
            cartDataSource.deleteProduct(productId) { result ->
                onResult(result)
            }
        }.onFailure { e ->
            Log.e("CartRepository", "delete failed", e)
        }
    }
}
