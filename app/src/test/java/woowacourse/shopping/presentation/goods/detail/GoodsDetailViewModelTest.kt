package woowacourse.shopping.presentation.goods.detail

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.GoodsRepositoryImpl
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.GOODS_SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsDetailViewModelTest {
    private lateinit var goodsDetailViewModel: GoodsDetailViewModel
    private val shoppingRepository: ShoppingRepository = mockk(relaxed = true)
    private val goods = GOODS_SUNDAE

    @BeforeEach
    fun setUp() {
        goodsDetailViewModel = GoodsDetailViewModel(GoodsRepositoryImpl, shoppingRepository)
        goodsDetailViewModel.setGoods(1)
    }

    @Test
    fun `장바구니에 항목을 추가할 수 있다`() {
        // given
        val id = slot<Int>()
        val count = slot<Int>()
        every { shoppingRepository.increaseItemQuantity(capture(id), capture(count)) } just Runs

        // when
        goodsDetailViewModel.addToShoppingCart()

        // then
        verify { shoppingRepository.increaseItemQuantity(any(), any()) }

        id.captured shouldBe goods.id
        count.captured shouldBe 1
    }

    @Test
    fun `장바구니에 추가할 상품 수량의 기본값은 1이다`() {
        // then
        goodsDetailViewModel.count.getOrAwaitValue() shouldBe 1
    }

    @Test
    fun `장바구니에 추가할 상품 수량을 증가할 수 있다`() {
        // when
        goodsDetailViewModel.increaseCount()

        // then
        goodsDetailViewModel.count.getOrAwaitValue() shouldBe 2
    }

    @Test
    fun `장바구니에 추가할 상품 수량을 감소할 수 있다`() {
        // given
        goodsDetailViewModel.increaseCount()

        // when
        goodsDetailViewModel.tryDecreaseCount()

        // then
        goodsDetailViewModel.count.getOrAwaitValue() shouldBe 1
    }
}
