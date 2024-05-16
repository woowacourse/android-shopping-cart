package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

class DefaultCartRepository : CartRepository {
    override fun cartProducts(): List<Product> {
        return listOf(
            Product(
                3,
                1000,
                "오둥2",
                "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba184931a88f7b2cbb72be0bdfff91ad65b168ab",
            ),
            Product(
                4,
                1000,
                "꼬상",
                "https://w7.pngwing.com/pngs/921/264/png-transparent-chipmunk-chip-n-dale-sticker-the-walt-disney-company-goofy-others.png",
            ),
        )
    }

    override fun deleteCartProduct(id: Long) {}
}