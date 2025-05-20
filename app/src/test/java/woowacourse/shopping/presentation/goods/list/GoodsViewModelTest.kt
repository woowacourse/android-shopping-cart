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
        goodsViewModel = GoodsViewModel()
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(GoodsDataBase)
    }

    @Test
    fun `상품 목록을 가져온다`() {
        // then
        goodsViewModel.goodsUiModels.getOrAwaitValue().isNotEmpty() shouldBe true
    }

    @Test
    fun `상품 목록을 추가한다`() {
        // given
        val before = goodsViewModel.goodsUiModels.getOrAwaitValue().size

        // when
        goodsViewModel.addGoods()
        val actual = goodsViewModel.goodsUiModels.getOrAwaitValue().size

        // then
        actual shouldBeGreaterThan before
    }

    @Test
    fun `데이터가 존재할 경우 데이터를 추가할 수 있다`() {
        // given
        every { GoodsDataBase.getPagedGoods(any(), any()) } returns listOf(createGoods())

        // when
        goodsViewModel.determineLoadMoreVisibility(false)

        // then
        goodsViewModel.showLoadMore.getOrAwaitValue() shouldBe true
    }

    @Test
    fun `데이터가 존재하지 않을 경우 데이터를 추가할 수 없다`() {
        // given
        every { GoodsDataBase.getPagedGoods(any(), any()) } returns emptyList()

        // when
        goodsViewModel.determineLoadMoreVisibility(false)

        // then
        goodsViewModel.showLoadMore.getOrAwaitValue() shouldBe false
    }
}
