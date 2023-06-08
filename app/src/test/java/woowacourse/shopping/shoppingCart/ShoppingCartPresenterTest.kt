package woowacourse.shopping.shoppingCart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartPresenter
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

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

    @Test
    fun testFetchProductsInCartByPage() {
        // given
        every { shoppingCartRepository.getShoppingCartByPage(any(), PAGE_NUMBER) } returns listOf(
            productCart,
        )

        val slot = slot<List<ProductInCartUiState>>()
        every { view.setShoppingCart(capture(slot)) } just runs

        // when
        presenter.fetchProductsInCartByPage(PAGE_NUMBER)

        // then
        val actual = slot.captured
        verify { view.setShoppingCart(actual) }

        Assert.assertEquals(wrappedShoppingCart, actual)
    }

    @Test
    fun testSetPageNumber() {
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
    fun testCheckPageMovement() {
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
    fun testDeleteProductInCart() {
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
        private val productCart = ProductInCart(product, QUANTITY, true)
        private val wrappedShoppingCart = listOf(ProductInCartUiState(product, 1, true))
    }
}
