package woowacourse.shopping.presentation.goods.detail

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.fixture.createGoods
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsDetailViewModelTest {
    private lateinit var goodsDetailViewModel: GoodsDetailViewModel
    private val goods = createGoods()

    @BeforeEach
    fun setUp() {
        goodsDetailViewModel = GoodsDetailViewModel()
    }

    @Test
    fun `장바구니에 항목을 추가할 수 있다`() {
        // when
        goodsDetailViewModel.setGoods(goods)

        // then
        goodsDetailViewModel.goods.getOrAwaitValue() shouldBe goods
    }

    @Test
    fun `장바구니에 추가하면 데이터베이스에 추가된다`() {
        // given
        goodsDetailViewModel.setGoods(goods)

        // when
        goodsDetailViewModel.addToShoppingCart()
        val actual = ShoppingDataBase.getAll()

        // then
        actual.contains(goods) shouldBe true
    }
}
