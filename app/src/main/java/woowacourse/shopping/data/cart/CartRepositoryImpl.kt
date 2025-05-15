package woowacourse.shopping.data.cart

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

    override fun fetchAll(callback: (List<Product>) -> Unit) {
        thread {
            val products = dao.findAll().map { productEntity -> productEntity.toProduct() }
            callback(products)
        }
    }

    override fun fetchPagedItems(limit: Int, offset: Int, callback: (List<Product>) -> Unit) {
        thread {
            val products = dao.findPagedItems(limit, offset).map { productEntity -> productEntity.toProduct() }
            callback(products)
        }
    }

    override fun remove(product: Product) {
        thread {
            dao.delete(product.toProductEntity())
        }
    }
}
