package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.local.cart.ShoppingCartDao
import kotlin.concurrent.thread

class LocalShoppingCartProductIdDataSource(private val dao: ShoppingCartDao) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Long): ProductIdsCountData? = dao.findById(productId)

    override fun loadPaged(page: Int): List<ProductIdsCountData> {
        val offset = (page - 1) * 5
        return dao.findPaged(offset)
    }

    override fun loadAll(): List<ProductIdsCountData> = dao.findAll()

    override fun isFinalPage(page: Int): Boolean {
        val count = dao.countAll()
        return page * 5 >= count
    }

    override fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Long = dao.insert(productIdsCountData)

    override fun removedProductsId(productId: Long): Long {
        val product = dao.findById(productId) ?: throw NoSuchElementException()
        dao.delete(productId)
        return product.productId
    }

    override fun plusProductsIdCount(productId: Long) {
        dao.increaseQuantity(productId)
    }

    override fun minusProductsIdCount(productId: Long) {
        val oldProduct = dao.findById(productId) ?: throw NoSuchElementException()
        val newProduct = oldProduct.copy(quantity = oldProduct.quantity - 1)

        dao.update(newProduct)
    }

    override fun clearAll() {
        dao.countAll()
    }

    // async function with callback
    override fun findByProductIdAsync(
        productId: Long,
        callback: (ProductIdsCountData?) -> Unit,
    ) {
        thread {
            val product = dao.findById(productId)
            callback(product)
        }
    }

    override fun loadPagedAsync(
        page: Int,
        callback: (List<ProductIdsCountData>) -> Unit,
    ) {
        thread {
            val offset = (page - 1) * 5
            callback(dao.findPaged(offset))
        }
    }

    override fun loadAllAsync(callback: (List<ProductIdsCountData>) -> Unit) {
        thread {
            val products = dao.findAll()
            callback(products)
        }
    }

    override fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    ) {
        thread {
            val count = dao.countAll()
            callback(page * 5 >= count)
        }
    }

    override fun addedNewProductsIdAsync(
        productIdsCountData: ProductIdsCountData,
        callback: (Long) -> Unit,
    ) {
        thread {
            val id = dao.insert(productIdsCountData)
            callback(id)
        }
    }

    override fun removedProductsIdAsync(
        productId: Long,
        callback: (Long) -> Unit,
    ) {
        thread {
            val product = dao.findById(productId) ?: throw NoSuchElementException()
            dao.delete(productId)
            callback(product.productId)
        }
    }

    override fun plusProductsIdCountAsync(
        productId: Long,
        callback: () -> Unit,
    ) {
        thread {
            dao.increaseQuantity(productId)
            callback()
        }
    }

    override fun minusProductsIdCountAsync(
        productId: Long,
        callback: () -> Unit,
    ) {
        thread {
            val oldProduct = dao.findById(productId) ?: throw NoSuchElementException()
            val newProduct = oldProduct.copy(quantity = oldProduct.quantity - 1)
            dao.update(newProduct)
            callback()
        }
    }

    override fun clearAllAsync() {
        thread {
            dao.countAll()
        }
    }

    override fun findByProductIdAsyncResult(productId: Long, callback: (Result<ProductIdsCountData?>) -> Unit) {
        thread {
            callback(
                runCatching {
                    dao.findById(productId)
                }
            )
        }
    }

    override fun loadPagedAsyncResult(page: Int, callback: (Result<List<ProductIdsCountData>>) -> Unit) {
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

    override fun isFinalPageAsyncResult(page: Int, callback: (Result<Boolean>) -> Unit) {
        thread {
            callback(runCatching { page * 5 >= dao.countAll() })
        }
    }

    override fun addedNewProductsIdAsyncResult(
        productIdsCountData: ProductIdsCountData,
        callback: (Result<Long>) -> Unit
    ) {
//        thread {
//            runCatching {
//                dao.insert(productIdsCountData)
//            }.let(callback)
//        }
        thread {
             callback(
                runCatching {
                    dao.insert(productIdsCountData)
                }
             )
        }

    }

    override fun removedProductsIdAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            runCatching {
                dao.delete(productId)
            }.let(callback)
        }
    }

    override fun plusProductsIdCountAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            callback(runCatching {
                dao.increaseQuantity(productId)
            })
        }
    }

    override fun minusProductsIdCountAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            callback(runCatching {
                val oldProduct = dao.findById(productId) ?: throw NoSuchElementException()
                val newProduct = oldProduct.copy(quantity = oldProduct.quantity - 1)
                dao.update(newProduct)
            })
        }
    }

    override fun clearAllAsyncResult(callback: (Result<Unit>) -> Unit) {
        thread {
            callback(runCatching { dao.clearAll() })
        }
    }

    companion object {
        private const val TAG = "LocalShoppingCartProductIdDataSource"
    }
}
