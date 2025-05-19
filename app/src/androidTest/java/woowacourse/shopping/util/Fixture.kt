@file:Suppress("ktlint:standard:filename")

import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods

val fixtureGoods =
    List(10) {
        Goods(
            "[굿즈-키홀더] 봇치 더 록! 애니판 테쿠토코 쵸이데카 아크릴키홀더B",
            13500,
            "https://animate.godohosting.com/Goods/4522776264043.jpg",
        )
    }

val fixtureRepository = FakeCartRepository()

class FakeCartRepository : CartRepository {
    override fun getAll(): List<Goods> = fixtureGoods

    override fun getAllItemsSize(): Int = 10

    override fun getPage(
        limit: Int,
        offset: Int,
    ): List<Goods> = emptyList()

    override fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        onComplete()
    }

    override fun delete(
        goods: Goods,
        onComplete: () -> Unit,
    ) { }
}
