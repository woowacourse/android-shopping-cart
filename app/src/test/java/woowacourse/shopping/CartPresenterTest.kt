package woowacourse.shopping

import com.shopping.domain.CartRepository
import com.shopping.domain.PageNumber
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.cart.CartContract
import woowacourse.shopping.cart.CartPresenter
import woowacourse.shopping.uimodel.CartProductUIModel

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: CartContract.Presenter

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk()
        cartRepository = mockk()
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `장바구니의 상품을 삭제한다`() {
        // given
        val cartProductUIModel = CartProductUIModel.dummy
        val position = 1
        every { cartRepository.remove(cartProductUIModel.product.id) } just runs
        every { view.removeAdapterData(cartProductUIModel, position) } just runs

        // when
        presenter.removeProduct(cartProductUIModel, position)

        // then
        verify { cartRepository.remove(cartProductUIModel.product.id) }
        verify { view.removeAdapterData(cartProductUIModel, position) }
    }

    @Test
    fun `장바구니 레포지토리로 부터 데이터를 가져와 뷰에 전달한다`() {
        // given
        every { cartRepository.getUnitData(any(), any()) } returns emptyList()
        every { view.setCartProducts(any()) } just runs

        // when
        presenter.getCartProducts()

        // then
        verify { cartRepository.getUnitData(any(), any()) }
        verify { view.setCartProducts(any()) }
    }

    @Test
    fun `다음 페이지가 보여줄 장바구니 아이템이 없다면 장바구니 다음 페이지로 넘어가지 않는다`() {
        // given
        val pageNumber: PageNumber = mockk()
        every { cartRepository.getSize() } returns 1
        every { pageNumber.value } returns 2

        // when
        presenter.goNextPage()

        // then
        verify(inverse = true) { presenter.setPageNumber() }
        verify(inverse = true) { pageNumber.nextPage() }
    }

    @Test
    fun `다음 페이지가 보여줄 장바구니 아이템이 있다면 장바구니 다음 페이지로 넘어간다`() {
        // given
        val pageNumber: PageNumber = mockk()
        every { cartRepository.getSize() } returns 6
        every { pageNumber.value } returns 1
        every { pageNumber.nextPage() } returns PageNumber(2)
        every { view.showPageNumber(2) } just runs
        every { presenter.changePage(any()) } just runs
        every { cartRepository.getUnitData(any(), any()) } returns emptyList()

        // when
        presenter.goNextPage()

        // then
        verify { presenter.setPageNumber() }
        verify { view.showPageNumber(any()) }
    }

    @Test
    fun `장바구니의 이전 페이지를 불러온다`() {
        // Arrange
        val pageNumber = mockk<PageNumber>()

        every { pageNumber.value } returns 5
        every { pageNumber.previousPage() } returns pageNumber
        every { view.showPageNumber(5) } just runs
        every { cartRepository.getSize() } returns 10
        every { cartRepository.getUnitData(any(), any()) } returns emptyList()

        // Act
        presenter.goPreviousPage()

        // Assert
        verify(exactly = 1) { pageNumber.previousPage() }
        verify(exactly = 1) { pageNumber.value }
        verify(exactly = 1) { view.showPageNumber(5) }
        verify(exactly = 1) { cartRepository.getSize() }
        verify(exactly = 1) { cartRepository.getUnitData(5, 0) }
        verify(exactly = 1) { view.setCartProducts(emptyList()) }
    }
}
