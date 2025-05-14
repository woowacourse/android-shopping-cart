package woowacourse.shopping.data.product

import kotlin.concurrent.thread

class CartRepositoryImpl(db: CartDatabase) : CartRepository {
    private val dao: CartDao = db.cartDao()

    override fun add(productEntity: ProductEntity) {
        thread {
            dao.insert(productEntity)
        }
    }

    override fun fetch(id: Long): ProductEntity {
        return dao.findById(id)
    }

    override fun fetchAll(): List<ProductEntity> {
        return dao.findAll()
    }

    override fun remove(productEntity: ProductEntity) {
        dao.delete(productEntity)
    }
}
