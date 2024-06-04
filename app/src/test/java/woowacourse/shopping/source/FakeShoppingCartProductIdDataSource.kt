package woowacourse.shopping.source

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import kotlin.concurrent.thread

class FakeShoppingCartProductIdDataSource(
    private val data: MutableList<ProductIdsCountData> = mutableListOf(),
) : ShoppingCartProductIdDataSource {
    override fun findByProductIdAsyncResultNonNull(
        productId: Long,
        callback: (Result<ProductIdsCountData>) -> Unit,
    ) {
        thread {
            runCatching {
                data.find { it.productId == productId } ?: throw NoSuchElementException()
            }.let(callback)
        }
    }

    override fun loadPagedAsyncResult(
        page: Int,
        callback: (Result<List<ProductIdsCountData>>) -> Unit,
    ) {
        thread {
            runCatching {
                val start = (page - 1) * PAGE_SIZE
                val end = start + PAGE_SIZE
                data.subList(start, end)
            }.let(callback)
        }
    }

    override fun loadAllAsyncResult(callback: (Result<List<ProductIdsCountData>>) -> Unit) {
        thread {
            runCatching {
                data
            }.let(callback)
        }
    }

    override fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    ) {
        thread {
            runCatching {
                val count = data.size
                page * PAGE_SIZE >= count
            }.let(callback)
        }
    }

    override fun addedNewProductsIdAsyncResult(
        productIdsCountData: ProductIdsCountData,
        callback: (Result<Long>) -> Unit,
    ) {
        thread {
            runCatching {
                data.add(productIdsCountData)
                productIdsCountData.productId
            }.let(callback)
        }
    }

    override fun removedProductsIdAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                val foundItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
                data.remove(foundItem)
                Unit
            }.let(callback)
        }
    }

    override fun plusProductsIdCountAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
                data.remove(oldItem)
                val newItem = oldItem.copy(quantity = oldItem.quantity + 1)
                data.add(newItem)
                Unit
            }.let(callback)
        }
    }

    override fun plusProductIdCountAsyncResult(
        productId: Long,
        quantity: Int,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
                data.remove(oldItem)
                val newItem = oldItem.copy(quantity = oldItem.quantity + quantity)
                data.add(newItem)
                Unit
            }.let(callback)
        }
    }

    override fun minusProductsIdCountAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
                data.remove(oldItem)
                data.add(oldItem.copy(quantity = oldItem.quantity - 1))
                Unit
            }.let(callback)
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
