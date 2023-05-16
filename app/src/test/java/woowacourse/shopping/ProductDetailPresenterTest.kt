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
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductDetailPresenterTest {

    private lateinit var view: ProductDetailContract.View
    private lateinit var productUIModel: ProductUIModel
    private lateinit var recentRepository: RecentRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: ProductDetailContract.Presenter

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productUIModel = ProductUIModel.dummy
        recentRepository = mockk()
        cartRepository = mockk()
        presenter = ProductDetailPresenter(view, productUIModel, recentRepository, cartRepository)
    }

    @Test
    fun `초기화면을 그릴 때 필요한 데이터를 넘겨준다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { view.setViews(productUIModel) } just runs

        // when
        presenter.initPage()

        // then
        verify { view.setViews(capture(slot)) }
        assertEquals(productUIModel, slot.captured)
    }

    @Test
    fun `최근 목록 저장소에 데이터를 추가한다`() {
        val currentTime = 1000L

        // given
        every {
            recentRepository.insert(
                RecentProductUIModel(
                    currentTime,
                    productUIModel
                )
            )
        } just runs

        // when
        presenter.insertRecentRepository(currentTime)

        // then
        verify {
            recentRepository.insert(
                RecentProductUIModel(
                    currentTime,
                    productUIModel
                )
            )
        }
    }

    @Test
    fun `장바구니에 추가하는 버튼이 클릭 될 때 데이터를 레포지토리에 저장하고 장바구니 페이지를 보여준다`() {
        // given
        every { cartRepository.insert(CartProductUIModel(1, productUIModel)) } just runs
        every { view.showCartPage() } just runs

        // when
        presenter.onClickAddToCart()

        // then
        verify { cartRepository.insert(CartProductUIModel(1, productUIModel)) }
        verify { view.showCartPage() }
    }
}
