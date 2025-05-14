package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.ProductEntity

interface ProductsStorage {
    val products: List<ProductEntity>
}
