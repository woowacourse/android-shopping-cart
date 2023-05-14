package woowacourse.shopping.feature.cart

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.CartProduct
import com.example.domain.repository.CartRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.model.PageUiModel

internal class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun init() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        every { cartRepository.getAll() } returns mockCartProducts
        presenter = CartPresenter(view, cartRepository)
        presenter.setPage(PageUiModel(41, 2))
    }

    @Test
    fun `이전 페이지를 불러온다`() {
        val pageSlot = slot<Int>()
        every { cartRepository.getProductsByPage(capture(pageSlot), any()) } returns emptyList()
        val cartProductSlot = slot<List<CartProductItemModel>>()
        every { view.changeCartProducts(capture(cartProductSlot)) } just Runs
        val previousSlot = slot<Boolean>()
        every { view.setPreviousButtonState(capture(previousSlot)) } just Runs
        val nextSlot = slot<Boolean>()
        every { view.setNextButtonState(capture(nextSlot)) } just Runs

        presenter.loadPreviousPage()

        assert(pageSlot.captured == 1)
        assert(previousSlot.captured.not())
        assert(nextSlot.captured)
    }

    @Test
    fun `다음 페이지를 불러온다`() {
        val pageSlot = slot<Int>()
        every { cartRepository.getProductsByPage(capture(pageSlot), any()) } returns emptyList()
        val cartProductSlot = slot<List<CartProductItemModel>>()
        every { view.changeCartProducts(capture(cartProductSlot)) } just Runs
        val previousSlot = slot<Boolean>()
        every { view.setPreviousButtonState(capture(previousSlot)) } just Runs
        val nextSlot = slot<Boolean>()
        every { view.setNextButtonState(capture(nextSlot)) } just Runs

        presenter.loadNextPage()

        assert(pageSlot.captured == 3)
        assert(previousSlot.captured)
        assert(nextSlot.captured)
    }

    private val mockCartProducts = List(41) {
        CartProduct(
            it.toLong(), productsDatasource[it]
        )
    }
}
