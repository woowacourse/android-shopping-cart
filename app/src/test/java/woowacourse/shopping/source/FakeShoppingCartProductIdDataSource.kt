package woowacourse.shopping.source

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import kotlin.concurrent.thread

class FakeShoppingCartProductIdDataSource(
    private val data: MutableList<ProductIdsCountData> = mutableListOf(),
) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Long): ProductIdsCountData? = data.find { it.productId == productId }

    override fun loadPaged(page: Int): List<ProductIdsCountData> {
        val start = (page - 1) * PAGE_SIZE
        val end = start + PAGE_SIZE
        return data.subList(start, end)
    }

    override fun loadAll(): List<ProductIdsCountData> = data

    override fun isFinalPage(page: Int): Boolean {
        val count = data.size
        return page * PAGE_SIZE >= count
    }

    override fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Long {
        data.add(productIdsCountData)
        return productIdsCountData.productId
    }

    override fun removedProductsId(productId: Long): Long {
        val foundItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(foundItem)
        return foundItem.productId
    }

    override fun plusProductsIdCount(productId: Long) {
        val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(oldItem)
        val newItem = oldItem.copy(quantity = oldItem.quantity + 1)
        data.add(newItem)
    }

    override fun minusProductsIdCount(productId: Long) {
        val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(oldItem)
        data.add(oldItem.copy(quantity = oldItem.quantity - 1))
    }

    override fun clearAll() {
        data.clear()
    }

    // fake async function with callback
    override fun findByProductIdAsync(
        productId: Long,
        callback: (ProductIdsCountData?) -> Unit,
    ) {
        thread {
            callback(findByProductId(productId))
        }
    }

    override fun loadPagedAsync(
        page: Int,
        callback: (List<ProductIdsCountData>) -> Unit,
    ) {
        thread {
            callback(loadPaged(page))
        }
    }

    override fun loadAllAsync(callback: (List<ProductIdsCountData>) -> Unit) {
        thread {
            callback(loadAll())
        }
    }

    override fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    ) {
        thread {
            callback(isFinalPage(page))
        }
    }

    override fun addedNewProductsIdAsync(
        productIdsCountData: ProductIdsCountData,
        callback: (Long) -> Unit,
    ) {
        thread {
            callback(addedNewProductsId(productIdsCountData))
        }
    }

    override fun removedProductsIdAsync(
        productId: Long,
        callback: (Long) -> Unit,
    ) {
        thread {
            callback(removedProductsId(productId))
        }
    }

    override fun plusProductsIdCountAsync(
        productId: Long,
        callback: () -> Unit,
    ) {
        thread {
            plusProductsIdCount(productId)
            callback()
        }
    }

    override fun minusProductsIdCountAsync(
        productId: Long,
        callback: () -> Unit,
    ) {
        thread {
            minusProductsIdCount(productId)
            callback()
        }
    }

    override fun clearAllAsync() {
        thread {
            clearAll()
        }
    }

    override fun findByProductIdAsyncResult(productId: Long, callback: (Result<ProductIdsCountData?>) -> Unit) {
        thread {
            runCatching {
                findByProductId(productId)
            }.let(callback)
        }
    }

    override fun loadPagedAsyncResult(page: Int, callback: (Result<List<ProductIdsCountData>>) -> Unit) {
        thread {
            runCatching {
                loadPaged(page)
            }.let(callback)
        }
    }

    override fun loadAllAsyncResult(callback: (Result<List<ProductIdsCountData>>) -> Unit) {
        thread {
            runCatching {
                loadAll()
            }.let(callback)
        }
    }

    override fun isFinalPageAsyncResult(page: Int, callback: (Result<Boolean>) -> Unit) {
        thread {
            runCatching {
                isFinalPage(page)
            }.let(callback)
        }
    }

    override fun addedNewProductsIdAsyncResult(
        productIdsCountData: ProductIdsCountData,
        callback: (Result<Long>) -> Unit
    ) {
        thread {
            runCatching {
                data.add(productIdsCountData)
                productIdsCountData.productId
            }.let(callback)
        }
    }

    override fun removedProductsIdAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            runCatching {
                removedProductsId(productId)
                Unit // TODO: 임시로 해둠
            }.let(callback)
        }
    }

    override fun plusProductsIdCountAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            runCatching {
                plusProductsIdCount(productId)
            }.let(callback)
        }
    }

    override fun minusProductsIdCountAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            runCatching {
                minusProductsIdCount(productId)
            }.let(callback)
        }
    }

    override fun clearAllAsyncResult(callback: (Result<Unit>) -> Unit) {
        thread {
            runCatching {
                clearAll()
            }.let(callback)
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
