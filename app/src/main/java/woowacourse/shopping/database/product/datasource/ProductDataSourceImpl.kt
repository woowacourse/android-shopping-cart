package woowacourse.shopping.database.product.datasource

import android.content.Context
import woowacourse.shopping.database.product.ProductEntity
import woowacourse.shopping.database.product.cache.ProductCache
import woowacourse.shopping.database.product.cache.ProductCacheImpl

class ProductDataSourceImpl(
    context: Context,
    private val productCache: ProductCache = ProductCacheImpl(context),
) : ProductDataSource {

    override fun getProductById(id: Int): ProductEntity {

        return productCache.getProductById(id)
    }

    override fun getProductInRange(from: Int, count: Int): List<ProductEntity> {

        return productCache.getProductInRange(
            from = from,
            count = count
        )
    }
}
