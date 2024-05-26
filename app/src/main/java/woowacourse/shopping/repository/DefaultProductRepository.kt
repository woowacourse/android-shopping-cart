package woowacourse.shopping.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.domain.GetLastProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import java.time.LocalDateTime

class DefaultProductRepository(
    private val recentProductDataSource: RecentProductDataSource,
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun products(): List<Product> = productDataSource.products().map { it.toProduct() }

    override fun products(
        startPosition: Int,
        offset: Int,
    ): List<Product> = productDataSource.productsByOffset(startPosition, offset).map { it.toProduct() }

    override fun productById(id: Long): Product = productDataSource.productById(id).toProduct()

    override fun productsTotalSize(): Int = productDataSource.products().size

    override fun sortedRecentProduct(): List<RecentProduct> =
        recentProductDataSource.recentProducts().sortedByDescending { it.lookDateTime }
            .map { it.toRecentProduct(productDataSource.productById(it.productId).toProduct()) }

    override fun addRecentProduct(
        productId: Long,
        localDateTime: LocalDateTime,
    ) {
        val recent = RecentProductEntity(productId, localDateTime)
        if (recentProductDataSource.recentProducts().none { recent.productId == it.productId })
            {
                recentProductDataSource.insert(recent)
            }
    }

    override fun lastRecentProduct(): GetLastProduct {
        val recent = recentProductDataSource.lastRecentProduct() ?: return GetLastProduct.Fail
        return GetLastProduct.Success(
            recent.toRecentProduct(
                productDataSource.productById(recent.productId).toProduct(),
            ),
        )
    }

    companion object {
        private var instance: DefaultProductRepository? = null

        fun initialize(
            recentProductDataSource: RecentProductDataSource,
            productDataSource: ProductDataSource,
        ): DefaultProductRepository =
            instance ?: synchronized(this) {
                instance ?: DefaultProductRepository(
                    recentProductDataSource, productDataSource,
                ).also { instance = it }
            }

        fun instance(): DefaultProductRepository = instance ?: error("RoomRepo가 초기화되지 않았습니다.")
    }
}
