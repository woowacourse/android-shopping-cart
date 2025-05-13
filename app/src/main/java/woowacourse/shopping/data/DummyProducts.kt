package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

object DummyProducts {
    val url =
        """https://github-production-user-asset-6210df.s3.amazonaws.com/95472545/443074183-8fc58beb-
            |267c-4ea7-a72b-836bbd4bfca2.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAV
            |CODYLSA53PQK4ZA%2F20250513%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250513T065320Z&
            |X-Amz-Expires=300&X-Amz-Signature=35f4c62ed7fabfe8d76e2f83cc158cab8cbf925163245e4d6bb19
            |330528e3d39&X-Amz-SignedHeaders=host
        """.trimMargin()

    val products =
        listOf(
            Product(
                "PET보틀-정사각형",
                10_000,
                url,
            ),
            Product(
                "PET보틀-밀크티",
                12_000,
                url,
            ),
            Product(
                "PET보틀-정사각형2",
                10_000,
                url,
            ),
            Product(
                "PET보틀-납작",
                12_000,
                url,
            ),
            Product(
                "PET보틀-단지",
                10_000,
                url,
            ),
            Product(
                "PET보틀-단지2",
                10_000,
                url,
            ),
        )
}
