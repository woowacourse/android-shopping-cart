package woowacourse.shopping.presentation.productlist

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productlist.ProductContract
import woowacourse.shopping.presentation.view.productlist.ProductListPresenter

class ProductListPresenterTest {
    private lateinit var presenter: ProductContract.Presenter
    private lateinit var view: ProductContract.View
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk()

        presenter = ProductListPresenter(view, productRepository)
    }

    @Test
    fun `데이터를 받아와 상품 목록 어댑터를 설정한다`() {
        // given
        every { productRepository.getData() } returns dummyData
        val slot = slot<List<ProductModel>>()
        justRun { view.setProductItemsView(capture(slot)) }

        // when
        presenter.loadProductItems()

        // then
        val actual = slot.captured
        val expected = dummyData.map { it.toUIModel() }

        assertEquals(expected, actual)
        verify { productRepository.getData() }
        verify { view.setProductItemsView(actual) }
    }

    companion object {
        private val dummyData = listOf(
            ProductEntity(
                id = 0,
                title = "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
                price = 24_900,
                imageUrl = "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
            )
        )
    }
}
