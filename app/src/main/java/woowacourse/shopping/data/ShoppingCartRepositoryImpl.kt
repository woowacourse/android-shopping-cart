package woowacourse.shopping.data

import woowacourse.shopping.MyApp
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.domain.Product

class ShoppingCartRepositoryImpl(
    private val dao: ShoppingCartDao = ShoppingCartDatabase.getDataBase(MyApp.applicationContext).productDao(),
) : ShoppingCartRepository {
    override fun getAll(): List<Product> = dao.getAll().toDomain()

    override fun insertAll(vararg product: Product) {
        dao.insertAll(*product.map { it.toEntity() }.toTypedArray())
    }

    override fun delete(product: Product) {
        dao.delete(product.toEntity())
    }
}
