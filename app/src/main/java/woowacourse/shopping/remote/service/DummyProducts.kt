package woowacourse.shopping.remote.service

import woowacourse.shopping.remote.model.ProductResponse

val STUB_PRODUCTS: List<ProductResponse> =
    List(20) {
        listOf(
            ProductResponse(
                1,
                1000,
                "오둥이",
                "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
            ),
            ProductResponse(
                2,
                1000,
                "오둥이",
                "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
            ),
            ProductResponse(
                3,
                1000,
                "오둥오둥",
                "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba184931a88f7b2cbb72be0bdfff91ad65b168ab",
            ),
            ProductResponse(
                4,
                1000,
                "꼬상",
                "https://w7.pngwing.com/pngs/921/264/" +
                    "png-transparent-chipmunk-chip-n-dale-sticker-the-walt-disney-company-goofy-others.png",
            ),
            ProductResponse(
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
