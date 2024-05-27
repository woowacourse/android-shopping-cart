
package woowacourse.shopping.remote

import woowacourse.shopping.data.model.ProductData

interface ProductApiService {
    fun loadById(id: Long): ProductData

    fun loadPaged(page: Int): List<ProductData>

    fun count(): Int

    fun shutDown(): Boolean
}
