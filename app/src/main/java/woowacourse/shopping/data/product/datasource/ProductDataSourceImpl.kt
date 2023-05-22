package woowacourse.shopping.data.product.datasource

import android.content.Context
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.product.remote.ProductRemote
import woowacourse.shopping.data.product.remote.ProductRemoteImpl

class ProductDataSourceImpl(
    context: Context,
    private val productRemote: ProductRemote = ProductRemoteImpl(),
) : ProductDataSource {

    override fun getProductById(id: Int): ProductEntity {

        return productRemote.getProductById(id)
    }

    override fun getProductInRange(from: Int, count: Int): List<ProductEntity> {

        return productRemote.getProductInRange(
            from = from,
            count = count
        )
    }
}
