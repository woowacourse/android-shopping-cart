package woowacourse.shopping.presentation.goods.list

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.GoodsDataBase
import woowacourse.shopping.fixture.createGoods
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsViewModelTest {
    private lateinit var goodsViewModel: GoodsViewModel

    @BeforeEach
    fun setUp() {
        mockkObject(GoodsDataBase)
        goodsViewModel = GoodsViewModel(GoodsDataBase)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(GoodsDataBase)
    }

    @Test
    fun `상품 목록을 정해진 수량만큼 가져온다`() {
        // then
        goodsViewModel.goods.getOrAwaitValue().size shouldBe 20
    }

    @Test
    fun `상품 목록을 추가한다`() {
        // given
        val before = goodsViewModel.goods.getOrAwaitValue().size

        // when
        goodsViewModel.addGoods()
        val actual = goodsViewModel.goods.getOrAwaitValue().size

        // then
        actual shouldBeGreaterThan before
    }

    @Test
    fun `데이터가 존재할 경우 로딩이 가능하다`() {
        // given
        every { GoodsDataBase.getPagedGoods(any(), any()) } returns listOf(createGoods())

        // when
        goodsViewModel.updateShouldShowLoadMore()
        val actual = goodsViewModel.shouldShowLoadMore.value!!

        // then
        actual shouldBe true
    }

    @Test
    fun `데이터가 존재하지 않을 경우 로딩이 불가능하다`() {
        // given
        every { GoodsDataBase.getPagedGoods(any(), any()) } returns emptyList()

        // when
        goodsViewModel.updateShouldShowLoadMore()
        val actual = goodsViewModel.shouldShowLoadMore.value!!

        // then
        actual shouldBe false
    }
}
