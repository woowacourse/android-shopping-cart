package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.CartItemEntity

interface ProductsStorage {
    fun load(): List<CartItemEntity>
}
