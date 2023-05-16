package woowacourse.shopping.utils

import woowacourse.shopping.domain.Product

object MockData {
    fun getProductList(): List<Product> {
        return (1..102).map { index ->
            Product(
                id = index.toLong(),
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvcCPDO5TRkS6bHmemx0262nWeXizH3fD8fJPsLHc2GxDqKCqMWeOYFK3HOJu5VKpaAH0&usqp=CAU",
                name = "Product $index",
                price = 10000,
            )
        }
    }
}
