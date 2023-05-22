package woowacourse.shopping.data.product.remote

import woowacourse.shopping.data.product.ProductEntity

interface ProductRemote {

    fun getProductById(id: Int): ProductEntity

    fun getProductInRange(from: Int, count: Int): List<ProductEntity>
}