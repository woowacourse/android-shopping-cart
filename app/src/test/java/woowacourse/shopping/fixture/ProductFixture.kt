package woowacourse.shopping.fixture

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product

val productFixture1 =
    Product(
        1L,
        "마리오 그린올리브 300g",
        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
        Price(3980),
        Quantity(0),
    )

val productFixture2 =
    Product(
        2L,
        "비비고 통새우 만두 200g",
        "https://images.emarteveryday.co.kr/images/product/8801392067167/vSYMPCA3qqbLJjhv.png",
        Price(81980),
        Quantity(10),
    )

val productFixture3 =
    Product(
        3L,
        "스테비아 방울토마토 500g",
        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
        Price(89860),
        Quantity(10),
    )

val productFixture4 =
    Product(
        4L,
        "디벨라 스파게티면 500g",
        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/85/00/8005121000085_1.png",
        Price(1980),
        Quantity(10),
    )
