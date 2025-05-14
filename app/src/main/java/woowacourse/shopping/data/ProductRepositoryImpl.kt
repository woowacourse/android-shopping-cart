package woowacourse.shopping.data

import woowacourse.shopping.data.CartMapper.toDomain
import woowacourse.shopping.data.CartMapper.toEntity
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class ProductRepositoryImpl(
    private val dao: CartDao,
) : ProductRepository {
    override fun getProducts(): List<Product> = DummyProducts.values

    override fun getCartProducts(): List<Product> = dao.getAllProduct().map { productEntity -> productEntity.toDomain() }

    override fun deleteProduct(product: Product) {
        val cartEntity = product.toEntity()
        dao.deleteProduct(cartEntity)
    }
}
