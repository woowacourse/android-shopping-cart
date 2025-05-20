package woowacourse.shopping.feature.goodsdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fixtureRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.getOrAwaitValue

@Suppress("ktlint:standard:function-naming")
class GoodsDetailsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GoodsDetailsViewModel
    private val testGoodsUiModel =
        GoodsUiModel(
            name = "테스트 상품",
            price = 10000,
            thumbnailUrl = "https://example.com/image.jpg",
        )

    @Before
    fun setup() {
        viewModel = GoodsDetailsViewModel(fixtureRepository)
    }

    @Test
    fun setGoods_호출시_상품정보가_설정된다() {
        // When
        viewModel.setGoods(testGoodsUiModel)

        // Then
        val goods = viewModel.goods.getOrAwaitValue()
        assertEquals("테스트 상품", goods.name)
        assertEquals(10000, goods.price)
        assertEquals("https://example.com/image.jpg", goods.thumbnailUrl)
    }

    @Test
    fun addToCart_호출시_장바구니에_상품이_추가되고_알림이_발생한다() {
        // Given
        viewModel.setGoods(testGoodsUiModel)

        // When
        viewModel.addToCart()
        Thread.sleep(500)

        // Then
        val alertEvent = viewModel.alertEvent.getOrAwaitValue()
        assertEquals(R.string.goods_detail_cart_insert_complete_toast_message, alertEvent)
    }
}
