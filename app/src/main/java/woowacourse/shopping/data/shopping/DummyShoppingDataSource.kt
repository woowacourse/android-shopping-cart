package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.Product

object DummyShoppingDataSource : ShoppingDataSource {
    var products: List<Product> =
        List(100) {
            listOf(
                Product(
                    1,
                    1000,
                    "오둥이",
                    "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
                ),
                Product(
                    2,
                    1000,
                    "오둥이",
                    "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
                ),
                Product(
                    3,
                    1000,
                    "오둥오둥",
                    "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba184931a88f7b2cbb72be0bdfff91ad65b168ab",
                ),
                Product(
                    4,
                    1000,
                    "꼬상",
                    "https://w7.pngwing.com/pngs/921/264/" +
                            "png-transparent-chipmunk-chip-n-dale-sticker-the-walt-disney-company-goofy-others.png",
                ),
                Product(
                    5,
                    1000,
                    "꼬상꼬상",
                    "https://i.namu.wiki/i/YvceZuAFsjYzbrTKYS09muExzVUw0f5JFBTAOLeCJbyeKghRLpkDnc5_XmQ9KvOpyRqz3zSWVZq5DpeW0HToWQ.webp",
                ),
            )
        }.flatten()
            .mapIndexed { index, product ->
                product.copy(
                    id = index + 1L,
                    name = "${product.name}${index + 1L}",
                )
            }

    override fun products(
        currentPage: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = currentPage * pageSize
        val toIndex = (fromIndex + pageSize).coerceAtMost(products.size)
        return products.subList(fromIndex, toIndex)
    }

    override fun productById(id: Long): Product {
        return products.find { it.id == id } ?: Product(0, 0, "", "")
    }

    override fun canLoadMoreProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        val fromIndex = currentPage * pageSize
        return fromIndex < products.size
    }

    override fun updateProductCount(
        id: Long,
        count: Int,
    ) {
        val updatedProducts =
            products.map {
                if (it.id == id) {
                    it.copy(count = count)
                } else {
                    it
                }
            }
        products = updatedProducts
    }
}
