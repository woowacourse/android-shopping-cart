package woowacourse.shopping.shoppingCart

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartPresenter

class ShoppingCartPresenterTest {
    private lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var view: ShoppingCartContract.View
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var pageNumber: PageNumber

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        shoppingCartRepository = mockk(relaxed = true)
        pageNumber = mockk(relaxed = true)
        presenter = ShoppingCartPresenter(view, shoppingCartRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun 장바구니를_가져와_뷰로_넘겨준다() {
        // given
        val productCart = ProductInCart(product, QUANTITY)
        every { shoppingCartRepository.getShoppingCartByPage(any(), PAGE_NUMBER) } returns listOf(
            productCart,
        )

        val slot = slot<List<ProductInCart>>()
        every { view.setShoppingCart(capture(slot)) } answers { nothing }

        // when
        presenter.getShoppingCart(PAGE_NUMBER)

        // then
        val actual = slot.captured
        verify { view.setShoppingCart(actual) }

        Assert.assertEquals(listOf(productCart), actual)
    }

    @Test
    fun 시작_페이지번호를_가져와_뷰로_넘겨준다() {
        // given
        val slot = slot<Int>()
        every { view.setPage(capture(slot)) } answers { nothing }

        // when
        presenter.setPageNumber()

        // then
        val actual = slot.captured
        verify { view.setPage(actual) }

        Assert.assertEquals(START_PAGE, actual)
    }

    @Test
    fun 데이터는_8개이며_첫_페이지에선_다음페이지로만_갈_수_있다() {
        // given
        every { shoppingCartRepository.getShoppingCartSize() } returns 8
        val previousSlot = slot<Boolean>()
        val nextSlot = slot<Boolean>()
        every {
            view.setPageButtonEnable(
                capture(previousSlot),
                capture(nextSlot),
            )
        } answers { nothing }

        // when
        presenter.checkPageMovement()

        // then
        val actualPrevious = previousSlot.captured
        val actualNext = nextSlot.captured
        verify { view.setPageButtonEnable(actualPrevious, actualNext) }

        Assert.assertEquals(true, actualNext)
        Assert.assertEquals(false, actualPrevious)
    }

    @Test
    fun 장바구니에서_특정_아이템을_삭제한다() {
        // given
        val productId = 1L
        val slot = slot<Long>()
        every { shoppingCartRepository.deleteProductInCart(capture(slot)) } returns true

        // when
        presenter.deleteProductInCart(productId)

        // then
        val actual = slot.captured
        verify { shoppingCartRepository.deleteProductInCart(actual) }

        Assert.assertEquals(productId, actual)
    }

    companion object {
        private const val START_PAGE = 1
        private const val QUANTITY = 1
        private const val PAGE_NUMBER = 1
        private val product = Product(0, "", "test", 999)
    }
}
