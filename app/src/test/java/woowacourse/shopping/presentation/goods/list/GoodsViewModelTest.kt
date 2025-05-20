package woowacourse.shopping.presentation.goods.list

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.goods.FakeGoodsRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsViewModelTest {
    private lateinit var goodsViewModel: GoodsViewModel
    private lateinit var repository: FakeGoodsRepository

    @BeforeEach
    fun setUp() {
        repository = FakeGoodsRepository()
        goodsViewModel = GoodsViewModel(repository)
    }

    @Test
    fun `상품 목록을 20개씩 가져온다`() {
        // then
        goodsViewModel.goodsUiModels.getOrAwaitValue().size shouldBe 20
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
        // when
        goodsViewModel.determineLoadMoreVisibility(false)

        // then
        goodsViewModel.showLoadMore.getOrAwaitValue() shouldBe true
    }

    @Test
    fun `데이터가 존재하지 않을 경우 데이터를 추가할 수 없다`() {
        // given
        val emptyRepository = FakeGoodsRepository(emptyList())
        val emptyViewModel = GoodsViewModel(emptyRepository)

        // when
        emptyViewModel.determineLoadMoreVisibility(false)

        // then
        emptyViewModel.showLoadMore.getOrAwaitValue() shouldBe false
    }
}
