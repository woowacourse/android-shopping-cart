package woowacourse.shopping.presentation.goods.detail

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsDetailViewModelTest {
    private lateinit var goodsDetailViewModel: GoodsDetailViewModel
    private val goods = SUNDAE

    @BeforeEach
    fun setUp() {
        goodsDetailViewModel = GoodsDetailViewModel(ShoppingDataBase)
    }

    @Test
    fun `장바구니에 항목을 추가할 수 있다`() {
        // when
        goodsDetailViewModel.setGoods(goods)

        // then
        goodsDetailViewModel.goods.getOrAwaitValue() shouldBe goods
    }
}
