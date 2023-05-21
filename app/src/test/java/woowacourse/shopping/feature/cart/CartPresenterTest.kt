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
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.mapper.toPresentation

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
    fun `처음에 첫 페이지를 불러온다`() {
        // when
        presenter.loadInitCartProduct()

        // then
        val actual = presenter.currentPageCartProducts.getOrAwaitValue()
        val expected = mockCartProducts.take(5).map { it.toPresentation() }
        assert(expected == actual)
    }

    @Test
    fun `주문아이디가 일치하는 주문 상품을 삭제한다`() {
        // given
        presenter.loadInitCartProduct()
        val slot = slot<CartProduct>()
        every { cartRepository.deleteProduct(capture(slot)) } just Runs

        // when
        presenter.handleDeleteCartProductClick(4L)

        // then
        verify { cartRepository.deleteProduct(mockCartProducts[4]) }
        val expected = 4L
        val actual = slot.captured

        assert(expected == actual.cartId)
    }

    @Test
    fun `이전 페이지를 불러온다`() {
        // given
        presenter.loadInitCartProduct()
        presenter.loadNextPage() //  2 페이지로 이동

        // when
        presenter.loadPreviousPage() // 1페이지로 이동

        // then
        val actual = presenter.currentPageCartProducts.getOrAwaitValue()
        val expected = mockCartProducts.take(5).map { it.toPresentation() } // 1페이지 데이터들
        assert(actual == expected)
    }

    @Test
    fun `다음 페이지를 불러온다`() {
        // given
        presenter.loadInitCartProduct() // 현재 1페이지

        // when
        presenter.loadNextPage() // 2페이지로 이동

        // then
        val actual = presenter.currentPageCartProducts.getOrAwaitValue()
        val expected = mockCartProducts.subList(5, 10).map { it.toPresentation() } // 2페이지 데이터들
        assert(actual == expected)
    }

    @Test
    fun `장바구니에서 카트 상품을 제거한다`() {
        // given
        presenter.loadInitCartProduct()
        val slot = slot<CartProduct>()
        every { cartRepository.deleteProduct(capture(slot)) } just Runs

        // when
        presenter.handleDeleteCartProductClick(4L)

        // then
        verify { cartRepository.deleteProduct(mockCartProducts[4]) }
        assert(mockCartProducts[4] == slot.captured)
    }

    @Test
    fun `장바구니에 화면에서 상품의 수량을 조정한다`() {
        // given
        presenter.loadInitCartProduct()
        val slot = slot<Product>()
        val countSlot = slot<Int>()
        every { cartRepository.changeCartProductCount(capture(slot), capture(countSlot)) } just Runs

        // when
        presenter.handleCartProductCartCountChange(0L, 3)

        // then
        verify { cartRepository.changeCartProductCount(any(), any()) }
        assert(productsDatasource[0] == slot.captured)
        assert(3 == countSlot.captured)
    }

    private val mockCartProducts = List(15) {
        CartProduct(
            it.toLong(), productsDatasource[it], it + 1, true
        )
    }

    private val mockProduct = Product(
        5,
        "유명산지 고당도사과 1.5kg",
        "https://product-image.kurly.com/cdn-cgi/image/quality=85,width=676/product/image/b573ba85-9bfa-433b-bafc-3356b081440b.jpg",
        Price(13000)
    )
}
