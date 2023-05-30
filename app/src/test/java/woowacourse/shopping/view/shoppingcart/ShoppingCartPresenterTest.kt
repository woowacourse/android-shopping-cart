package woowacourse.shopping.view.shoppingcart

import com.shopping.domain.CartProduct
import com.shopping.domain.Count
import com.shopping.domain.Product
import com.shopping.repository.CartProductRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.model.uimodel.mapper.toDomain
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ShoppingCartPresenterTest {
    private lateinit var view: ShoppingCartContract.View
    private lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var cartProductRepository: CartProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartProductRepository = mockk(relaxed = true)
        presenter = ShoppingCartPresenter(view, cartProductRepository)
    }

    @Test
    fun `x_버튼을_누르면_해당_상품이_삭제된다`() {
        // given
        val slot = slot<CartProduct>()
        val cartProduct = cartProduct(1).toUIModel()
        every { cartProductRepository.remove(capture(slot)) } just runs

        // when
        presenter.removeCartProduct(cartProduct)

        // then
        verify { cartProductRepository.remove(cartProduct.toDomain()) }
        val expected = cartProduct.toDomain()
        val actual = slot.captured

        assert(expected == actual)
    }

    @Test
    fun `선택된_상품들의_총_갯수를_구할_수_있다`() {
        // given
        val slot = slot<Int>()
        every { cartProductRepository.getAll() } returns cartProducts()
        every { view.updateTotalCount(capture(slot)) } just runs

        // when
        presenter.updateSelectedTotal()

        // then
        val expected = cartProducts().sumOf { it.count.value }
        val actual = slot.captured

        assert(expected == actual)
    }

    @Test
    fun `선택된_상품들의_총_가격을_구할_수_있다`() {
        // given
        val slot = slot<Int>()
        every { cartProductRepository.getAll() } returns cartProducts()
        every { view.updateTotalPrice(capture(slot)) } just runs

        // when
        presenter.updateSelectedTotal()

        // then
        val expected = cartProducts().sumOf { it.product.price }
        val actual = slot.captured

        assert(expected == actual)
    }

    @Test
    fun `다음_페이지_버튼을_누르면_페이지가_증가한다`() {
        // given
        val expected = presenter.paging.getPageCount() + 1
        val slot = slot<Int>()
        every { view.updatePageCounter(capture(slot)) } just runs

        // when
        presenter.loadNextPage(true)

        // then
        verify { view.updatePageCounter(2) }

        val actual = slot.captured
        assert(expected == actual)
    }

    @Test
    fun `비활성화된_이전_페이지_버튼을_누르전_페이지가_감소하지_않고_유지된다`() {
        // given
        val expected = presenter.paging.getPageCount()
        val slot = slot<Int>()
        every { view.updatePageCounter(capture(slot)) } just runs

        // when
        presenter.loadPreviousPage(true)

        // then
        verify { view.updatePageCounter(1) }

        val actual = slot.captured
        assert(expected == actual)
    }

    private fun cartProducts(): List<CartProduct> = List(10) {
        CartProduct(Product(it, "", "", 1000), Count(1), true)
    }

    private fun cartProduct(id: Int): CartProduct =
        CartProduct(Product(id, "", "", 1000), Count(1), true)
}
