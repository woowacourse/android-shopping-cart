package woowacourse.shopping.data

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
}
