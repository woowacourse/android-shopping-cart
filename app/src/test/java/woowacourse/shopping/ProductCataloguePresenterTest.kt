package woowacourse.shopping

import com.shopping.domain.CartRepository
import com.shopping.domain.ProductRepository
import com.shopping.domain.RecentRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.productcatalogue.ProductCatalogueContract
import woowacourse.shopping.productcatalogue.ProductCataloguePresenter
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductCataloguePresenterTest {

    private lateinit var view: ProductCatalogueContract.View
    private lateinit var presenter: ProductCatalogueContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk()
        recentProductRepository = mockk()
        cartRepository = mockk()
        presenter = ProductCataloguePresenter(
            view,
            productRepository,
            recentProductRepository,
            cartRepository
        )
    }

    @Test
    fun `최근 목록 상품 데이터를 가져와서 뷰에게 넘겨준다`() {
        // given
        val slot = slot<List<RecentProductUIModel>>()
        every {
            recentProductRepository.getAll()
        } returns listOf(RecentProductUIModel(0L, ProductUIModel.dummy).toDomain())

        every { view.setRecentProductList(capture(slot)) } just runs

        // when
        presenter.getRecentProduct()

        // then
        verify { view.setRecentProductList(slot.captured) }
        assertEquals(listOf(RecentProductUIModel(0L, ProductUIModel.dummy)), slot.captured)
    }

    @Test
    fun `더보기가 클릭 되었을 때 데이터를 가져오고 뷰에게 데이터가 변화함을 알린다`() {
        // given
        val unitSize = 20
        val page = 1
        val slotUnitSize = slot<Int>()
        val slotPageSize = slot<Int>()
        val products = listOf(ProductUIModel.dummy.toDomain())

        every {
            productRepository.getUnitData(capture(slotUnitSize), capture(slotPageSize))
        } returns products

        every { view.attachNewProducts() } just runs

        // when
        presenter.getNewProducts(unitSize, page)

        // then
        verify { productRepository.getUnitData(unitSize, page) }
        verify { view.attachNewProducts() }
        assertEquals(unitSize, slotUnitSize.captured)
        assertEquals(page, slotPageSize.captured)
    }

    @Test
    fun `장바구니 아이콘 옆 개수를 최신화 한다`() {
        // given
        every { cartRepository.getSize() } returns 3

        // when
        presenter.updateCartCount()

        // then
        verify(exactly = 1) { view.setCartCountCircle(any()) }
    }

    @Test
    fun `장바구니 내 상품의 개수를 수정한다`() {
        // given
        val product = ProductUIModel.dummy
        val count = 2
        every { cartRepository.updateProductCount(product.id, count) } just runs
        // when
        presenter.updateCartProductCount(product, count)

        // then
        verify { cartRepository.updateProductCount(product.id, count) }
    }

    @Test
    fun `상품의 개수를 가져온다`() {
        // given
        val product = ProductUIModel.dummy
        every { cartRepository.getProductCount(any()) } returns 2
        // when
        val actual = presenter.getProductCount(product)

        // then
        assertEquals(actual, 2)
    }
}
