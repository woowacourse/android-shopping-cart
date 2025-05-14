package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    applicationContext: Context,
    private val dao: ShoppingCartDao =
        ShoppingCartDatabase.getDataBase(applicationContext).productDao(),
) : ShoppingCartRepository {
    override fun getAll(): List<Product> {
        var result = listOf<Product>()
        thread {
            result = dao.getAll().toDomain()
        }.join()
        return result
    }

    override fun insertAll(vararg product: Product) {
        thread {
            dao.insertAll(*product.map { it.toEntity() }.toTypedArray())
        }.join()
    }

    override fun delete(productId: Long) {
        thread {
            dao.delete(productId)
        }.join()
    }
}
