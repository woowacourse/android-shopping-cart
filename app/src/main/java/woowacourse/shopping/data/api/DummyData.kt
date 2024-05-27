package woowacourse.shopping.data.api

import woowacourse.shopping.model.Product

val MAC_BOOK =
    Product(
        imageUrl =
            """
            https://file.bodnara.co.kr/logo/insidelogo.php?image=%2Fhttp%3A%2F%2Ffile.bodnara.co.kr%2Fwebedit%2Fnews%2F2010%2F1403651148-mba_12.jpg
            """.trimIndent(),
        name = "맥북",
        price = 100,
    )

val dummyProductsData =
    (0..50).map {
        MAC_BOOK.copy(id = it.toLong(), name = "맥북$it")
    }
