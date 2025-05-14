package woowacourse.shopping.data.product

import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity
import kotlin.concurrent.thread

class CartRepositoryImpl(db: CartDatabase) : CartRepository {
    private val dao: CartDao = db.cartDao()

    override fun add(product: Product) {
        thread {
            dao.insert(product.toProductEntity())
        }
    }

    override fun fetch(id: Long): Product {
        return dao.findById(id).toProduct()
    }

    override fun fetchAll(): List<Product> {
        return dao.findAll().map { productEntity -> productEntity.toProduct() }
    }

    override fun remove(product: Product) {
        return dao.delete(product.toProductEntity())
    }
}
