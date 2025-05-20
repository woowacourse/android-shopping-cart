package woowacourse.shopping.data.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val dao: ProductDao,
) : ProductRepository {
    override fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (List<Product>) -> Unit,
    ) {
        thread {
            val productEntities: List<ProductEntity> = dao.findInRange(limit, offset)
            val products: List<Product> =
                productEntities.map { productEntity -> productEntity.toProduct() }
            onResult(products)
        }
    }

    override fun fetchById(
        id: Long,
        onResult: (Product) -> Unit,
    ) {
        thread {
            val product = dao.findById(id).toProduct()
            onResult(product)
        }
    }

    override fun insertAll(vararg products: Product) {
        thread {
            val productEntities =
                products.map { product -> product.toProductEntity() }.toTypedArray()
            dao.insertAll(*productEntities)
        }
    }
}
