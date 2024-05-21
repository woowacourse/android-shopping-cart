package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.RecentRepository
import java.time.LocalDateTime

object DummyRecentRepository : RecentRepository {
    private val recentProducts: List<RecentProductItem> =
        listOf(
            RecentProductItem(
                productId = 0,
                name = "0",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 1,
                name = "1",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 2,
                name = "2",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 3,
                name = "3",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 4,
                name = "4",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 5,
                name = "5",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 6,
                name = "6",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
            RecentProductItem(
                productId = 7,
                name = "7",
                imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                dateTime = LocalDateTime.now(),
            ),
        )

    override fun load(): Result<List<RecentProductItem>> =
        runCatching {
            recentProducts
        }
}
