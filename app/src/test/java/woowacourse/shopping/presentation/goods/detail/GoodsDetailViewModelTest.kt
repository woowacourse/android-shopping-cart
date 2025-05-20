package woowacourse.shopping.presentation.goods.detail

import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.model.toUiModel

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsDetailViewModelTest {
    private lateinit var goodsDetailViewModel: GoodsDetailViewModel
    private lateinit var repository: ShoppingCartRepository
    private val goods = SUNDAE.toUiModel()

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        goodsDetailViewModel = GoodsDetailViewModel(goods, repository)
    }

    @Test
    fun `상품 정보가 저장된다`() {
        // then
        goodsDetailViewModel.goodsUiModel.getOrAwaitValue() shouldBe goods
    }

    @Test
    fun `장바구니에 상품을 저장한다`() {
        // when
        goodsDetailViewModel.addToShoppingCart()

        // then
        verify { repository.addGoods(any()) }
    }
}
