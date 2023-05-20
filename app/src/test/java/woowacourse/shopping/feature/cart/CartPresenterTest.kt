package woowacourse.shopping.feature.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.CartProduct
import com.example.domain.model.Price
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

internal class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        every { cartRepository.getAll() } returns mockCartProducts
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `처음 5개를 가져와 화면에 띄운다`() {
        every { cartRepository.getProductsByPage(any(), any()) } returns mockCartProducts.take(5)
        val cartProductSlot = slot<List<CartProductUiModel>>()
        every { view.changeCartProducts(capture(cartProductSlot)) } just Runs

        presenter.setup()

        val expected = mockCartProducts.take(5)
        val actual = cartProductSlot.captured.map {
            CartProduct(it.productUiModel.toDomain(), 1, true)
        }

        assert(expected == actual)
        verify { view.changeCartProducts(any()) }
        verify { view.setPageState(any(), any(), any()) }
    }

    @Test
    fun `이전 페이지를 불러온다`() {
        presenter.setPage(PageUiModel(41, 2))
        val pageSlot = slot<Int>()
        val previousSlot = slot<Boolean>()
        val nextSlot = slot<Boolean>()
        every {
            view.setPageState(
                capture(previousSlot),
                capture(nextSlot),
                capture(pageSlot)
            )
        } just Runs

        presenter.loadPreviousPage()

        assert(pageSlot.captured == 1)
        assert(previousSlot.captured.not())
        assert(nextSlot.captured)
    }

    @Test
    fun `다음 페이지를 불러온다`() {
        presenter.setPage(PageUiModel(41, 2))
        val pageSlot = slot<Int>()
        val previousSlot = slot<Boolean>()
        val nextSlot = slot<Boolean>()
        every {
            view.setPageState(
                capture(previousSlot),
                capture(nextSlot),
                capture(pageSlot)
            )
        } just Runs

        presenter.loadNextPage()

        assert(pageSlot.captured == 3)
        assert(previousSlot.captured)
        assert(nextSlot.captured)
    }

    private val mockCartProducts = List(41) {
        CartProduct(
            productsDatasource[it], 1, true
        )
    }

    private val mockProduct = Product(
        5,
        "유명산지 고당도사과 1.5kg",
        "https://product-image.kurly.com/cdn-cgi/image/quality=85,width=676/product/image/b573ba85-9bfa-433b-bafc-3356b081440b.jpg",
        Price(13000)
    )

    private val mockCartProductUiModel = CartProductUiModel(
        mockProduct.toPresentation(1), 1, true
    )
}
