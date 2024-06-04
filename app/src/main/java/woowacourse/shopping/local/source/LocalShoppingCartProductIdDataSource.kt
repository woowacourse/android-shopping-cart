package woowacourse.shopping.local.source

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.local.cart.ShoppingCartDao
import kotlin.concurrent.thread

class LocalShoppingCartProductIdDataSource(private val dao: ShoppingCartDao) : ShoppingCartProductIdDataSource {
    override fun findByProductIdAsyncResultNonNull(
        productId: Long,
        callback: (Result<ProductIdsCountData>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    dao.findById(productId) ?: throw NoSuchElementException()
                },
            )
        }
    }

    override fun loadPagedAsyncResult(
        page: Int,
        callback: (Result<List<ProductIdsCountData>>) -> Unit,
    ) {
        thread {
            runCatching {
                val offset = (page - 1) * 5
                dao.findPaged(offset)
            }.let(callback)
        }
    }

    override fun loadAllAsyncResult(callback: (Result<List<ProductIdsCountData>>) -> Unit) {
        thread {
            callback(runCatching { dao.findAll() })
        }
    }

    override fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    ) {
        thread {
            callback(runCatching { page * 5 >= dao.countAll() })
        }
    }

    override fun addedNewProductsIdAsyncResult(
        productIdsCountData: ProductIdsCountData,
        callback: (Result<Long>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    dao.insert(productIdsCountData)
                },
            )
        }
    }

    override fun removedProductsIdAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                dao.delete(productId)
            }.let(callback)
        }
    }

    override fun plusProductsIdCountAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    dao.increaseQuantity(productId, CHANGE_AMOUNT)
                },
            )
        }
    }

    override fun plusProductIdCountAsyncResult(
        productId: Long,
        quantity: Int,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    dao.increaseQuantity(productId, quantity)
                },
            )
        }
    }

    override fun minusProductsIdCountAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    val oldProduct = dao.findById(productId) ?: throw NoSuchElementException()
                    val newProduct = oldProduct.copy(quantity = oldProduct.quantity - 1)
                    dao.update(newProduct)
                },
            )
        }
    }

    companion object {
        private const val TAG = "LocalShoppingCartProductIdDataSource"
        private const val CHANGE_AMOUNT = 1
    }
}
