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

    override fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, DummyProducts.values.size)
        if (fromIndex >= DummyProducts.values.size) return emptyList()
        return DummyProducts.values.subList(fromIndex, toIndex)
    }

    override fun getCartProducts(): List<Product> = dao.getAllProduct().map { productEntity -> productEntity.toDomain() }

    override fun getPagedCartProducts(
        pageSize: Int,
        page: Int,
    ): List<Product> {
        val offset = page * pageSize
        return dao
            .getPagedProduct(pageSize, offset)
            .map { productEntity -> productEntity.toDomain() }
    }

    override fun insertProduct(product: Product) {
        dao.insertProduct(product.toEntity())
    }

    override fun deleteProduct(productId: Long) {
        dao.deleteByProductId(productId = productId)
    }
}
