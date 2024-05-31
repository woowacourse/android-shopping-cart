package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.local.cart.ShoppingCartDao
import kotlin.concurrent.thread

class LocalShoppingCartProductIdDataSource(private val dao: ShoppingCartDao) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Long): ProductIdsCountData? = dao.findById(productId)

    override fun loadPaged(page: Int): List<ProductIdsCountData> = dao.findPaged(page)

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
            val products = dao.findPaged(page)
            callback(products)
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

    companion object {
        private const val TAG = "LocalShoppingCartProductIdDataSource"
    }
}
