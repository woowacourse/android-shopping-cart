package woowacourse.shopping.feature.cart

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
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.CartProductUiModel

internal class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun init() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        every { cartRepository.getAll() } returns mockCartProducts
        every { cartRepository.getInitPageProducts(any()) } returns mockCartProducts.take(5)
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `처음 5개를 가져와 화면에 띄운다`() {
        // given
        val cartProductSlot = slot<List<CartProductUiModel>>()
        every { view.updateCartProducts(capture(cartProductSlot)) } just Runs

        // when
        presenter.loadInitCartProduct()

        // then
        val expected = mockCartProducts.take(5)
        val actual =
            cartProductSlot.captured.map { CartProduct(it.cartId, it.productUiModel.toDomain()) }
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
        val slot = slot<Long>()
        every { cartRepository.getPreviousProducts(any(), capture(slot)) } answers {
            mockCartProducts.filter { it.cartId < slot.captured }.take(5)
        }
        val cartProductSlot = slot<List<CartProductUiModel>>()
        every { view.updateCartProducts(capture(cartProductSlot)) } just Runs
        presenter.loadInitCartProduct()

        // when
        presenter.loadPreviousPage()

        // then
        val actual = cartProductSlot.captured.map { it.toDomain() }
        val expected = mockCartProducts.take(5)
        assert(actual == expected)
    }

    @Test
    fun `다음 페이지를 불러온다`() {
        // given
        val slot = slot<Long>()
        every { cartRepository.getNextProducts(any(), capture(slot)) } answers {
            mockCartProducts.filter { it.cartId > slot.captured }.take(5)
        }
        val cartProductSlot = slot<List<CartProductUiModel>>()
        every { view.updateCartProducts(capture(cartProductSlot)) } just Runs
        presenter.loadInitCartProduct()

        // when
        presenter.loadNextPage()

        // then
        val actual = cartProductSlot.captured.map { it.toDomain() }
        val expected = mockCartProducts.subList(5, 10)
        assert(actual == expected)
    }

    private val mockCartProducts = List(15) {
        CartProduct(
            it.toLong(), productsDatasource[it]
        )
    }

    private val mockProduct = Product(
        5,
        "유명산지 고당도사과 1.5kg",
        "https://product-image.kurly.com/cdn-cgi/image/quality=85,width=676/product/image/b573ba85-9bfa-433b-bafc-3356b081440b.jpg",
        Price(13000)
    )
}
