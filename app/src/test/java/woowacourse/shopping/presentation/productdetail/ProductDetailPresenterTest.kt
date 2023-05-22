package woowacourse.shopping.presentation.productdetail

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.presentation.ProductFixture
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.productdetail.ProductDetailContract
import woowacourse.shopping.presentation.view.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)

        cartRepository = mockk()
        productRepository = mockk()
        every { productRepository.loadDataById(0L) } returns ProductFixture.getData()

        presenter = ProductDetailPresenter(view, 0L, productRepository, cartRepository)
    }

    @Test
    fun `id를 통해 데이터를 받아와 상품 정보를 보여준다`() {
        // given
        val slot = slot<ProductModel>()
        justRun { view.setProductInfoView(capture(slot)) }

        // when
        presenter.loadProductInfo()

        // then
        val actual = slot.captured
        val expected = ProductFixture.getData().toUIModel()

        assertEquals(expected, actual)
        verify { productRepository.loadDataById(actual.id) }
        verify { view.setProductInfoView(actual) }
    }

    @Test
    fun `장바구니 담기를 누르면 상품의 개수를 정하는 다이얼로그가 보여진다`() {
        // given
        val product = ProductFixture.getData().toUIModel()
        justRun { view.showCountView(product) }

        // when
        presenter.showCount()

        // then
        verify { view.showCountView(product) }
    }

    @Test
    fun `마지막으로 본 상품을 보여준다`() {
        // given
        val product = ProductFixture.getData().toUIModel()
        val lastRecentProduct = RecentProductModel(1L, product)
        val slot = slot<RecentProductModel>()
        justRun { view.setVisibleOfLastRecentProductInfoView(capture(slot)) }

        // when
        presenter.loadLastRecentProductInfo(lastRecentProduct)

        // then
        val actual = slot.captured
        assertEquals(lastRecentProduct, actual)
        verify { view.setVisibleOfLastRecentProductInfoView(actual) }
    }

    @Test
    fun `마지막으로 본 상품이 존재하지 않으면 보여지지 않는다`() {
        // given
        val product = ProductModel(-1L, "", 0, "")
        val lastRecentProduct = RecentProductModel(-1L, product)
        justRun { view.setGoneOfLastRecentProductInfoView() }

        // when
        presenter.loadLastRecentProductInfo(lastRecentProduct)

        // then
        verify { view.setGoneOfLastRecentProductInfoView() }
    }

    @Test
    fun `상품을 장바구니에 1개 저장한다`() {
        // given
        val slotId = slot<Long>()
        val slotCount = slot<Int>()
        justRun { cartRepository.addCart(capture(slotId), capture(slotCount)) }
        justRun { view.addCartSuccessView() }
        justRun { view.exitProductDetailView() }

        // when
        presenter.addCart(1)

        // then
        val actualId = slotId.captured
        val actualCount = slotCount.captured
        val expectedId = 0L
        val expectedCount = 1
        assertEquals(expectedId, actualId)
        assertEquals(expectedCount, actualCount)
        verify { cartRepository.addCart(actualId, actualCount) }
        verify { view.addCartSuccessView() }
        verify { view.exitProductDetailView() }
    }
}
