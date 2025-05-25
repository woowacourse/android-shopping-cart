@file:Suppress("ktlint:standard:filename")

import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods

val fixtureCartItem =
    listOf(
        CartItem(
            Goods(
                "[굿즈-키홀더] 봇치 더 록! 애니판 테쿠토코 쵸이데카 아크릴키홀더B",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
            2,
        ),
        CartItem(
            Goods(
                "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/렘",
                10500,
                "https://animate.godohosting.com/Goods/4550621224034.jpg",
            ),
            2,
        ),
        CartItem(
            Goods(
                "[굿즈-스탠드팝] 진격의 거인 아크릴스탠드 리바이A",
                18000,
                "https://animate.godohosting.com/Goods/4582682486458.jpg",
            ),
            2,
        ),
        CartItem(
            Goods(
                "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/람",
                10500,
                "https://animate.godohosting.com/Goods/4550621224041.jpg",
            ),
            2,
        ),
        CartItem(
            Goods(
                "[굿즈-클리어파일] SPY x FAMILY WIT스토어선행 클리어파일 샵 비주얼",
                6000,
                "https://animate.godohosting.com/Goods/4549743820774.jpg",
            ),
            2,
        ),
        CartItem(
            Goods(
                "[굿즈-마우스패드] BanG Dream! 뱅드림 Ani-Art 마우스패드 제5탄 Pastel＊Palettes",
                27000,
                "https://animate.godohosting.com/Goods/4571622733271.jpg",
            ),
            2,
        ),
        CartItem(
            Goods(
                "[굿즈-스탠드팝] VOCALOID 하츠네 미쿠 39Culture 2024 지방 아크릴 디오라마 후쿠오카",
                18000,
                "https://animate.godohosting.com/Goods/4582660578458.jpg",
            ),
            2,
        ),
    )

val fixtureGoodsItem =
    List(10) {
        listOf(
            Goods(
                "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/렘",
                10500,
                "https://animate.godohosting.com/Goods/4550621224034.jpg",
            ),
            Goods(
                "[굿즈-키홀더] 봇치 더 록! 애니판 테쿠토코 쵸이데카 아크릴키홀더B",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
            Goods(
                "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/람",
                10500,
                "https://animate.godohosting.com/Goods/4550621224041.jpg",
            ),
        )
    }.flatten()

class FakeCartRepository : CartRepository {
    private val cartItems = fixtureCartItem.toMutableList()

    override fun fetchAllCartItems(onComplete: (List<CartItem>) -> Unit) {
        onComplete(cartItems)
    }

    override fun fetchPageCartItems(
        limit: Int,
        offset: Int,
        onComplete: (List<CartItem>) -> Unit,
    ) {
        onComplete(getPage(limit, offset))
    }

    private fun getPage(
        limit: Int,
        offset: Int,
    ): List<CartItem> {
        val endIndex = minOf(offset + limit, cartItems.size)
        return if (offset < cartItems.size) {
            cartItems.subList(offset, endIndex)
        } else {
            emptyList()
        }
    }

    override fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        val existingItem = cartItems.find { it.goods.name == goods.name }
        if (existingItem == null) {
            cartItems.add(CartItem(goods, 1))
        }
        onComplete()
    }

    override fun addOrIncreaseQuantity(
        goods: Goods,
        addQuantity: Int,
        onComplete: () -> Unit,
    ) {
        val existingItemIndex = cartItems.indexOfFirst { it.goods.name == goods.name }
        if (existingItemIndex != -1) {
            val existingItem = cartItems[existingItemIndex]
            cartItems[existingItemIndex] =
                existingItem.copy(quantity = existingItem.quantity + addQuantity)
        } else {
            cartItems.add(CartItem(goods, addQuantity))
        }
        onComplete()
    }

    override fun removeOrDecreaseQuantity(
        goods: Goods,
        removeQuantity: Int,
        onComplete: () -> Unit,
    ) {
        val existingItemIndex = cartItems.indexOfFirst { it.goods.name == goods.name }
        if (existingItemIndex != -1) {
            val existingItem = cartItems[existingItemIndex]
            val newQuantity = existingItem.quantity - removeQuantity
            if (newQuantity <= 0) {
                cartItems.removeAt(existingItemIndex)
            } else {
                cartItems[existingItemIndex] = existingItem.copy(quantity = newQuantity)
            }
        }
        onComplete()
    }

    override fun delete(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        cartItems.removeAll { it.goods.name == goods.name }
        onComplete()
    }

    override fun getAllItemsSize(onComplete: (Int) -> Unit) {
        onComplete(cartItems.size)
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
        val goods = goodsList.getOrNull(id)
        onComplete(goods)
    }

    override fun fetchRecentGoodsIds(onComplete: (List<String>) -> Unit) {
        onComplete(recentGoodsIds.take(10))
    }

    override fun fetchRecentGoods(onComplete: (List<Goods>) -> Unit) {
        val recentGoods =
            recentGoodsIds.take(5).mapNotNull { id ->
                goodsList.find { it.name == id }
            }
        onComplete(recentGoods)
    }

    override fun fetchMostRecentGoods(onComplete: (Goods?) -> Unit) {
        val mostRecentId = recentGoodsIds.firstOrNull()
        val mostRecentGoods =
            mostRecentId?.let { id ->
                goodsList.find { it.name == id }
            }
        onComplete(mostRecentGoods)
    }

    override fun loggingRecentGoods(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        recentGoodsIds.remove(goods.name)
        recentGoodsIds.add(0, goods.name)

        if (recentGoodsIds.size > 10) {
            recentGoodsIds.removeAt(recentGoodsIds.size - 1)
        }

        onComplete()
    }
}

val fixtureCartRepository = FakeCartRepository()
val fixtureGoodsRepository = FakeGoodsRepository()
