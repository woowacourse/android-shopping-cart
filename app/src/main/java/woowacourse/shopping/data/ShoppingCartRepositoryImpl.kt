package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.domain.Product

class ShoppingCartRepositoryImpl(
    applicationContext: Context,
    private val dao: ShoppingCartDao =
        ShoppingCartDatabase.getDataBase(applicationContext).productDao(),
) : ShoppingCartRepository {
    override fun getAll(): List<Product> = dao.getAll().toDomain()

    override fun insertAll(vararg product: Product) {
        dao.insertAll(*product.map { it.toEntity() }.toTypedArray())
    }

    override fun delete(product: Product) {
        dao.delete(product.toEntity())
    }
}
