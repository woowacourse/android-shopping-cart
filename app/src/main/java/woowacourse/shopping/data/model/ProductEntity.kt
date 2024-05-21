package woowacourse.shopping.data.model

import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.Product

data class ProductEntity(val id: Long, val name: String, val price: Int, val imageUrl: String) {
    fun toProduct(): Product {
        return Product(
            id = id,
            price = price,
            imageUrl = imageUrl,
            name = name,
        )
    }
}
