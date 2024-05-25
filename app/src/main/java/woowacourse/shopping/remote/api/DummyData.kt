package woowacourse.shopping.remote.api

import woowacourse.shopping.data.model.remote.ProductDto

object DummyData {
    const val STUB_IMAGE_URL_A =
        "https://i.namu.wiki/i/VnSgJ92KZ4dSRF2_x3LAYiE-zafxvNochXYrt6QD88DNtVziOxYUVKploFydbFNY7rcmOBUEra42XObzSuBwww.webp"
    val STUB_PRODUCT_A =
        ProductDto(id = 1L, name = "홍차", price = 10000, imageUrl = STUB_IMAGE_URL_A)

    const val STUB_IMAGE_URL_B =
        "https://img.danawa.com/prod_img/500000/451/474/img/3474451_1.jpg?_v=20210323160420"
    val STUB_PRODUCT_B =
        ProductDto(id = 2, name = "스위트 콘", price = 12000, imageUrl = STUB_IMAGE_URL_B)

    const val STUB_IMAGE_URL_C =
        "https://i.namu.wiki/i/fhsBMFdIgnB_D4KHQpaG0n2yk5X26rVpfsYeoIaJxwb3gLbQDJ9C7rgVQEZWKfhUwE0bR_2yT0Y1FCOwkDePJg.webp"
    val STUB_PRODUCT_C =
        ProductDto(id = 3, name = "신라면", price = 15000, imageUrl = STUB_IMAGE_URL_C)

    val CART_PRODUCTS =
        mutableListOf(
            STUB_PRODUCT_B.copy(id = 0),
            STUB_PRODUCT_B.copy(id = 1),
            STUB_PRODUCT_C.copy(id = 2),
            STUB_PRODUCT_A.copy(id = 3),
            STUB_PRODUCT_B.copy(id = 4),
            STUB_PRODUCT_C.copy(id = 5),
            STUB_PRODUCT_A.copy(id = 6),
            STUB_PRODUCT_B.copy(id = 7),
            STUB_PRODUCT_C.copy(id = 8),
            STUB_PRODUCT_A.copy(id = 9),
            STUB_PRODUCT_B.copy(id = 10),
        )

    val PRODUCT_LIST =
        mutableListOf(
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_A,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
            STUB_PRODUCT_B,
            STUB_PRODUCT_C,
        ).mapIndexed { index, product ->
            product.copy(
                id = (index + 1).toLong(),
                name = "$index ${product.name}",
            )
        }.toMutableList()
}
