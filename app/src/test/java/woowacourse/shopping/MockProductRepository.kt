package woowacourse.shopping

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class MockProductRepository : ProductRepository {
    val products =
        arrayListOf(
            Product(
                id = 0L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-단지(400ml) 레몬청",
            ),
            Product(
                id = 1L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/72c2/0af4/2caf0cd056a7448894e1c0f424483cf8?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=KmEmRkC1tOHZ0SvRrag5S20e6NGzhi5TIEw2CNIEjq7uvG0~Dc0gI4teeJpfMVgslP9IkReydFyE5MQwns11D~0POXZGWqp6cQMk4BIjG0tyAou4kWNtn9glMJZPizXd6NvYJ9QBTYS6WH9ELgGMa8wp6~v9AqvDdDQuCquR2jwDtCd6kqnB5Qx5FlCgpWCQ-Bms1Q324WXky--1Zxwd4ug7yGA0JqeqU~HiT0amjWh6HLk-zB2tJ-G-nhzM1Q49qQDkpF1knozIYpju5x16DUwSW2O6nIrZZFm-IjObGAVN-zBNuv4CC2v0Syjd3iPIz8q7lPZNN2vy~s7VvNytOw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-납작(2000ml) 밀크티",
            ),
            Product(
                id = 2L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/1e9f/f403/c410c794ab4d26aa3aa0c0c2eab7cb36?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=JLgPHKD8pgLS6Fhfw9hjusvszdk0GBM5T7xDDdgyyFbrRe~UcEv-9HrxsDwxdzbWBU~f80Y1kQrt4T2JqDqMVdUeQPNyhcKdca7~k06QPwPy5BQl01IiCCR--OdYwNitwXTr917kkfrdUY6HBeEGMZD~v1MydZqLuGi0~Xwaickjd34pz-5g3JoGmDISNh8nRw0p-Vlb0dUPSgKCkmONZ9bmGX26TpWU~urwZlh6gq7zTu6zDSzNYHhP-4W78rbOZVI~07fWWzlZOhi~rI9W53qI5XeRHkvKikl40tB2v09PpM6mFbqZfVVoFZ0iLWxw8k39bT0YXnLgia0Pn-pFxw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-밀크티(600ml)",
            ),
        )

    override fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        repeat(5) {
            products.addAll(products)
        }
        return products.subList(offset, pagingSize)
    }

    override fun getProduct(productId: Long): Product {
        return products.firstOrNull { it.id == productId } ?: defaultProduct
    }

    companion object {
        private const val DEFAULT_ID = -1L
        private val defaultProduct =
            Product(
                id = DEFAULT_ID,
                name = "",
                price = 0,
                imageUrl = "",
            )
    }
}
