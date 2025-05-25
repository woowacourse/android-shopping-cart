package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.toEntity
import woowacourse.shopping.data.shoppingCart.dao.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.entity.ShoppingCartProductEntity
import woowacourse.shopping.data.shoppingCart.entity.toEntity
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartStorage
import woowacourse.shopping.data.shoppingCart.storage.VolatileShoppingCartStorage
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct
import kotlin.concurrent.thread

class DefaultShoppingCartRepository(
    private val shoppingCartStorage: ShoppingCartStorage = VolatileShoppingCartStorage,
    private val shoppingCartDao: ShoppingCartDao,
) : ShoppingCartRepository {
    override fun load(
        offset: Int,
        limit: Int,
        onResult: (Result<List<ShoppingCartProduct>>) -> Unit,
    ) {
        thread {
            runCatching {
                shoppingCartDao
                    .getShoppingCartProducts(offset, offset + limit)
                    .map(ShoppingCartProductEntity::toDomain)
            }.onSuccess { productList ->
                onResult(Result.success(productList))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun add(
        product: Product,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val shoppingCartProduct = ShoppingCartProduct(product, quantity)
        thread {
            runCatching {
                shoppingCartDao.insertShoppingCart(shoppingCartProduct.toEntity())
            }.onSuccess {
                onResult(Result.success(Unit))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun decreaseQuantity(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                shoppingCartStorage.decreaseQuantity(product.toEntity())
            }.onSuccess {
                onResult(Result.success(Unit))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun remove(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                shoppingCartStorage.remove(product.toEntity())
            }.onSuccess {
                onResult(Result.success(Unit))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun fetchSelectedQuantity(
        product: Product,
        onResult: (Result<Int>) -> Unit,
    ) {
        thread {
            runCatching {
                shoppingCartStorage.fetchQuantity(product.toEntity())
            }.onSuccess { quantity: Int ->
                onResult(Result.success(quantity))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun fetchSelectedQuantity(
        products: List<Product>,
        onResult: (Result<List<ShoppingCartProduct>>) -> Unit,
    ) {
        thread {
            runCatching {
                products.map { product ->
                    ShoppingCartProduct(
                        product = product,
                        quantity = shoppingCartStorage.fetchQuantity(product.toEntity()),
                    )
                }
            }.onSuccess { shoppingCartProducts: List<ShoppingCartProduct> ->
                onResult(Result.success(shoppingCartProducts))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun fetchAllQuantity(onResult: (Result<Int>) -> Unit) {
        thread {
            runCatching {
                shoppingCartStorage.quantity
            }.onSuccess { allQuantity: Int ->
                onResult(Result.success(allQuantity))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    companion object {
        private var INSTANCE: ShoppingCartRepository? = null

        fun initialize(shoppingCartDao: ShoppingCartDao) {
            if (INSTANCE == null) {
                INSTANCE = DefaultShoppingCartRepository(shoppingCartDao = shoppingCartDao)
            }
        }

        fun get(): ShoppingCartRepository = INSTANCE ?: throw IllegalStateException("초기화 되지 않았습니다.")
    }
}
