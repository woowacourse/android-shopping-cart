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
import woowacourse.shopping.mapper.toPresentation
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
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `처음 5개를 가져와 화면에 띄운다`() {
        val cartProductSlot = slot<List<CartProductItemModel>>()
        every { view.changeCartProducts(capture(cartProductSlot)) } just Runs
        val previousSlot = slot<Boolean>()
        every { view.setPreviousButtonState(capture(previousSlot)) } just Runs
        val nextSlot = slot<Boolean>()
        every { view.setNextButtonState(capture(nextSlot)) } just Runs

        presenter.loadInitCartProduct()

        val expected = mockCartProducts.take(5)
        val actual = cartProductSlot.captured.map {
            CartProduct(it.cartProduct.cartId, it.cartProduct.productUiModel.toDomain())
        }

        assert(expected == actual)
        assert(!previousSlot.captured)
        assert(nextSlot.captured)
    }

    @Test
    fun `상품 하나를 삭제하고 다시 불러온 상품을 화면에 띄운다`() {
        val slot = slot<Long>()
        every { cartRepository.getPageCartProductsFromFirstId(5, capture(slot)) } returns emptyList()
        every { cartRepository.deleteProduct(any()) } just Runs

        presenter.deleteCartProduct(mockCartProductUiModel, 5L)

        verify { cartRepository.deleteProduct(any()) }
        val expected = 5L
        val actual = slot.captured

        assert(expected == actual)
    }

    @Test
    fun `이전 페이지를 불러온다`() {
        val slot = slot<Long>()
        every { cartRepository.getPreviousProducts(any(), capture(slot)) } answers {
            mockCartProducts.filter { it.cartId < slot.captured }.take(5)
        }
        val cartProductSlot = slot<List<CartProductItemModel>>()
        every { view.changeCartProducts(capture(cartProductSlot)) } just Runs
        val previousSlot = slot<Boolean>()
        every { view.setPreviousButtonState(capture(previousSlot)) } just Runs
        val nextSlot = slot<Boolean>()
        every { view.setNextButtonState(capture(nextSlot)) } just Runs

        presenter.loadPreviousPage(5)

        val actual = cartProductSlot.captured.map { it.cartProduct.toDomain() }
        val expected = mockCartProducts.take(5)
        assert(actual == expected)
    }

    @Test
    fun `다음 페이지를 불러온다`() {
        val slot = slot<Long>()
        every { cartRepository.getNextProducts(any(), capture(slot)) } answers {
            mockCartProducts.filter { it.cartId > slot.captured }.take(5)
        }
        val cartProductSlot = slot<List<CartProductItemModel>>()
        every { view.changeCartProducts(capture(cartProductSlot)) } just Runs
        val previousSlot = slot<Boolean>()
        every { view.setPreviousButtonState(capture(previousSlot)) } just Runs
        val nextSlot = slot<Boolean>()
        every { view.setNextButtonState(capture(nextSlot)) } just Runs

        presenter.loadNextPage(4)

        val actual = cartProductSlot.captured.map { it.cartProduct.toDomain() }
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

    private val mockCartProductUiModel = CartProductUiModel(
        5L, mockProduct.toPresentation()
    )
}
