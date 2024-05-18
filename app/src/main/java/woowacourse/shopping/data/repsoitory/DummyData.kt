package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.Product

object DummyData {
    const val STUB_IMAGE_URL_A =
        "https://i.namu.wiki/i/VnSgJ92KZ4dSRF2_x3LAYiE-zafxvNochXYrt6QD88DNtVziOxYUVKploFydbFNY7rcmOBUEra42XObzSuBwww.webp"
    val STUB_PRODUCT_A = Product(1, "홍차", 10000, STUB_IMAGE_URL_A)

    const val STUB_IMAGE_URL_B =
        "https://img.danawa.com/prod_img/500000/451/474/img/3474451_1.jpg?_v=20210323160420"
    val STUB_PRODUCT_B = Product(2, "스위트 콘", 12000, STUB_IMAGE_URL_B)

    const val STUB_IMAGE_URL_C =
        "https://i.namu.wiki/i/fhsBMFdIgnB_D4KHQpaG0n2yk5X26rVpfsYeoIaJxwb3gLbQDJ9C7rgVQEZWKfhUwE0bR_2yT0Y1FCOwkDePJg.webp"
    val STUB_PRODUCT_C = Product(3, "신라면", 15000, STUB_IMAGE_URL_C)

    val order =
        Order(
            id = 1,
            product = STUB_PRODUCT_A,
        )

    val ORDERS =
        mutableListOf(
            order,
            order.copy(id = 2, product = STUB_PRODUCT_B),
            order.copy(id = 3, product = STUB_PRODUCT_C),
            order.copy(id = 4, product = STUB_PRODUCT_A),
            order.copy(id = 5, product = STUB_PRODUCT_B),
            order.copy(id = 6, product = STUB_PRODUCT_C),
            order.copy(id = 7, product = STUB_PRODUCT_A),
            order.copy(id = 8, product = STUB_PRODUCT_B),
            order.copy(id = 9, product = STUB_PRODUCT_C),
            order.copy(id = 10, product = STUB_PRODUCT_A),
            order.copy(id = 11, product = STUB_PRODUCT_B),
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
        ).mapIndexed { index, product -> product.copy(id = index + 1) }.toMutableList()
}
