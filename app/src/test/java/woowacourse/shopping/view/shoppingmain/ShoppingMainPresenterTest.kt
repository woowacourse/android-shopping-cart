package woowacourse.shopping.view.shoppingmain

import com.shopping.domain.CartProduct
import com.shopping.domain.Count
import com.shopping.domain.Product
import com.shopping.domain.RecentProduct
import com.shopping.repository.CartProductRepository
import com.shopping.repository.ProductRepository
import com.shopping.repository.RecentProductsRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ShoppingMainPresenterTest {
    private lateinit var view: ShoppingMainContract.View
    private lateinit var presenter: ShoppingMainContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var cartProductRepository: CartProductRepository
    private lateinit var recentProductRepository: RecentProductsRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        cartProductRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        presenter = ShoppingMainPresenter(
            view,
            productRepository,
            cartProductRepository,
            recentProductRepository
        )
    }

    @Test
    fun `상단바를_통해_현재_장바구니에_담긴_상품_개수를_알_수_있다`() {
        // given
        val expected = cartProducts().sumOf { it.count.value }
        val slot = slot<Int>()
        every { cartProductRepository.getAllProductsCount() } returns expected
        every { view.updateCartBadgeCount(capture(slot)) } just runs

        // when
        presenter.updateCartBadge()

        // then
        val actual = slot.captured
        assert(expected == actual)
    }

    @Test
    fun `최신_상품_목록을_확인_할_수_있다`() {
        // given
        every { recentProductRepository.getAll() } returns recentProducts()

        // when
        presenter.getRecentProducts()

        // then
        verify { recentProductRepository.getAll() }
    }

    @Test
    fun `id로_장바구니에_담은_개수를_찾을_수_있다`() {
        // given
        val product = product(1)
        val slot = slot<Int>()
        every { cartProductRepository.findCountById(capture(slot)) } returns 1

        // when
        presenter.updateProductCartCount(product.toUIModel())

        // then
        verify { cartProductRepository.findCountById(product.id) }
        val expected = product.id
        val actual = slot.captured

        assert(expected == actual)
    }

    private fun recentProducts(): List<RecentProduct> = List(10) {
        RecentProduct(Product(it, "", "", 1000))
    }

    private fun cartProducts(): List<CartProduct> = List(10) {
        CartProduct(Product(it, "", "", 1000), Count(1), true)
    }

    private fun product(id: Int): Product =
        Product(id, "", "", 1000)
}
