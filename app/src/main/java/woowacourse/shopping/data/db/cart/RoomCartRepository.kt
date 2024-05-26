package woowacourse.shopping.data.db.cart

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class RoomCartRepository(private val dao: CartDao) : CartRepository {
    override fun modifyQuantity(
        product: Product,
        quantityDelta: Int,
    ): Result<Long> {
        var result: Result<Long>? = null
        thread {
            result =
                runCatching {
                    dao.modifyQuantity(product.id, quantityDelta)
                    dao.find(product.id)?.let {
                        if (it.quantity <= 0) dao.delete(it.product.id)
                    } ?: run {
                        if (quantityDelta > 0) setQuantity(product, quantityDelta)
                    }
                    product.id
                }
        }.join()
        return result ?: throw IllegalArgumentException()
    }

    override fun setQuantity(
        product: Product,
        newQuantityValue: Int,
    ): Result<Long> {
        var result: Result<Long>? = null
        thread {
            result =
                runCatching {
                    if (newQuantityValue == 0) {
                        dao.delete(product.id)
                    } else {
                        val affectedRows = dao.setQuantity(product.id, newQuantityValue)
                        if (affectedRows == 0) {
                            dao.insert(CartEntity(0, product.id, product, newQuantityValue))
                        }
                    }
                    product.id
                }
        }.join()
        return result ?: throw IllegalArgumentException()
    }

    override fun deleteProduct(product: Product): Result<Long> {
        var result: Result<Long>? = null
        thread {
            result =
                runCatching {
                    dao.delete(product.id)
                    product.id
                }
        }.join()
        return result ?: throw IllegalArgumentException()
    }

    override fun find(product: Product): Result<Cart> {
        var result: Result<Cart>? = null
        thread {
            result =
                runCatching {
                    dao.find(product.id)?.toDomain() ?: throw NoSuchElementException()
                }
        }.join()
        return result ?: throw IllegalArgumentException()
    }

    override fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Cart>> {
        var result: Result<List<Cart>>? = null
        thread {
            result =
                runCatching { dao.loadPage(startPage, pageSize).map { it.toDomain() } }
        }.join()
        return result ?: throw IllegalArgumentException()
    }

    override fun loadAll(): Result<List<Cart>> {
        var result: Result<List<Cart>>? = null
        thread {
            result =
                runCatching {
                    val a = dao.loadAll().map { it.toDomain() }
                    a
                }
        }.join()
        return result ?: throw IllegalArgumentException()
    }

    override fun getMaxPage(pageSize: Int): Result<Int> {
        var result: Result<Int>? = null
        thread {
            result =
                runCatching {
                    val itemCount = dao.countItems()
                    ((itemCount - 1) / pageSize).coerceAtLeast(0)
                }
        }.join()
        return result ?: throw IllegalArgumentException()
    }
}
