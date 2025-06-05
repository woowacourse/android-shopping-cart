@file:Suppress("ktlint:standard:filename")

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.domain.model.Goods

val fixtureGoodsItem =
    run {
        val baseGoods =
            listOf(
                Triple(
                    "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/렘",
                    10500,
                    "https://animate.godohosting.com/Goods/4550621224034.jpg",
                ),
                Triple(
                    "[굿즈-키홀더] 봇치 더 록! 애니판 테쿠토코 쵸이데카 아크릴키홀더B",
                    13500,
                    "https://animate.godohosting.com/Goods/4522776264043.jpg",
                ),
                Triple(
                    "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/람",
                    10500,
                    "https://animate.godohosting.com/Goods/4550621224041.jpg",
                ),
            )

        (0 until 10).flatMap { i ->
            baseGoods.mapIndexed { j, (name, price, url) ->
                val id = i * baseGoods.size + j + 1
                Goods(name, price, url, id.toLong())
            }
        }
    }

class FakeGoodsRepository : GoodsRepository {
    private val goodsList = fixtureGoodsItem
    private val recentGoodsIds = mutableListOf<String>()

    override fun fetchGoodsSize(onComplete: (Int) -> Unit) {
        onComplete(goodsList.size)
    }

    override fun fetchPageGoods(
        limit: Int,
        offset: Int,
        onComplete: (List<Goods>) -> Unit,
    ) {
        val endIndex = minOf(offset + limit, goodsList.size)
        val pageGoods =
            if (offset < goodsList.size) {
                goodsList.subList(offset, endIndex)
            } else {
                emptyList()
            }
        onComplete(pageGoods)
    }

    override fun fetchGoodsById(
        id: Int,
        onComplete: (Goods?) -> Unit,
    ) {
        val goods = goodsList.find { it.id == id.toLong() }
        onComplete(goods)
    }

    override fun fetchRecentGoodsIds(onComplete: (List<String>) -> Unit) {
        onComplete(recentGoodsIds.take(10))
    }

    override fun fetchRecentGoods(onComplete: (List<Goods>) -> Unit) {
        val recentGoods =
            recentGoodsIds.take(5).mapNotNull { idString ->
                val id = idString.toLongOrNull()
                if (id != null) {
                    goodsList.find { it.id == id }
                } else {
                    goodsList.find { it.name == idString }
                }
            }
        onComplete(recentGoods)
    }

    override fun fetchMostRecentGoods(onComplete: (Goods?) -> Unit) {
        Handler(Looper.getMainLooper()).post {
            val mostRecentId = recentGoodsIds.firstOrNull()
            val mostRecentGoods =
                mostRecentId?.let { idString ->
                    val id = idString.toLongOrNull()
                    if (id != null) {
                        goodsList.find { it.id == id }
                    } else {
                        goodsList.find { it.name == idString }
                    }
                }
            onComplete(mostRecentGoods)
        }
    }

    override fun loggingRecentGoods(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        val goodsId = goods.id.toString()
        recentGoodsIds.remove(goodsId)
        recentGoodsIds.add(0, goodsId)

        if (recentGoodsIds.size > 10) {
            recentGoodsIds.removeAt(recentGoodsIds.size - 1)
        }

        onComplete()
    }
}
