package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.datas.ProductRepository
import woowacourse.shopping.productcatalogue.ProductCatalogueContract
import woowacourse.shopping.productcatalogue.ProductCataloguePresenter
import woowacourse.shopping.uimodel.ProductUIModel

class ProductCataloguePresenterTest {

    private lateinit var view: ProductCatalogueContract.View
    private lateinit var presenter: ProductCatalogueContract.Presenter
    private val productUIModel = ProductUIModel.dummy

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = ProductCataloguePresenter(view)
    }

    @Test
    fun `상품이 클릭 되었을 때 페이지를 이동할 데이터를 전달한다`() {
        // given
        every { view.showProductDetailPage(productUIModel) } just runs
        val slot = slot<ProductUIModel>()

        // when
        presenter.productOnClick()(productUIModel)

        // then
        verify { view.showProductDetailPage(capture(slot)) }
        assertEquals(productUIModel, slot.captured)
    }

    @Test
    fun `더보기가 클릭 되었을 때 데이터를 가져오고 뷰에게 데이터가 변화함을 알린다`() {
        // given
        val unitSize = 20
        val page = 1
        val productData: ProductRepository = mockk()
        val slotUnitSize = slot<Int>()
        val slotPageSize = slot<Int>()
        every { productData.getUnitData(unitSize, page) } returns listOf(ProductUIModel.dummy)
        every { view.notifyDataChanged() } just runs

        // when
        presenter.readMoreOnClick()(productData, unitSize, page)

        // then
        verify { productData.getUnitData(capture(slotUnitSize), capture(slotPageSize)) }
        verify { view.notifyDataChanged() }
        assertEquals(unitSize, slotUnitSize.captured)
        assertEquals(page, slotPageSize.captured)
    }
}
