package woowacourse.shopping.repository.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.ProductDao
import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.data.remote.mapper.toEntity
import woowacourse.shopping.data.remote.product.ProductHttpClient
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RoomProductRepository(
    private val productDao: ProductDao,
    private val productHttpClient: ProductHttpClient,
) : ProductRepository {
    override fun getAllProducts(): Flow<Products> =
        productDao.getAllProducts().map { entities ->
            Products(entities.map { it.toDomain() })
        }

    override suspend fun getProductById(productId: Uuid): Product? = productDao.getProductById(productId = productId.toString())?.toDomain()

    override suspend fun refreshProducts() {
        val products = productHttpClient.getProducts().map { it.toEntity() }
        productDao.upsertAll(products)
    }
}
