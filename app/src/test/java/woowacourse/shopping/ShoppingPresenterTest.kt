package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.Product
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.shopping.ShoppingContract
import woowacourse.shopping.shopping.ShoppingPresenter
import woowacourse.shopping.util.toUiModel

class ShoppingPresenterTest {

    private lateinit var exampleProduct: Product
    private lateinit var exampleProducts: List<Product>
    private lateinit var view: ShoppingContract.View
    private lateinit var repository: ShoppingRepository

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        view = mockk(relaxed = true)
        exampleProducts = listOf(
            Product(name = "아메리카노"),
            Product(name = "카페라떼")
        )
        exampleProduct = Product(name = "밀크티")
    }

    @Test
    fun `저장소로부터 상품 목록을 받아와서 뷰를 초기화한다`() {
        // given
        every { repository.selectProducts(any(), any()) } returns exampleProducts

        // when
        val presenter = ShoppingPresenter(
            view = view,
            repository = repository
        )

        presenter.loadProducts()

        // then
        val expected = exampleProducts.map { it.toUiModel() }

        verify {
            view.setUpShoppingView(
                products = expected,
                any(),
                any()
            )
        }
    }

    @Test
    fun `저장소로부터 최근 본 상품을 받아와서 뷰를 초기화한다`() {
        // given
        every { repository.selectRecentViewedProducts() } returns exampleProducts

        // when
        val presenter = ShoppingPresenter(
            view = view,
            repository = repository
        )

        presenter.loadProducts()

        // then
        val expected = exampleProducts.map { it.toUiModel() }

        verify {
            view.setUpShoppingView(
                any(),
                recentViewedProducts = expected,
                any()
            )
        }
    }

    @Test
    fun `상품을 더 읽어오면 추가적으로 받아온 상품들을 가지고 뷰를 갱신한다`() {
        // given
        every { repository.selectProducts(any(), any()) } returns exampleProducts

        // when
        val presenter = ShoppingPresenter(
            view = view,
            repository = repository
        )

        presenter.readMoreShoppingProducts()

        // then
        val expected = exampleProducts.map { it.toUiModel() }

        verify { view.refreshShoppingProductsView(toAdd = expected) }
    }

    @Test
    fun `최근 본 상품을 추가하면 저장소에 저장한 후 기존 최근 본 상품들과 합쳐서 뷰를 갱신한다`() {
        // given
        every { repository.selectRecentViewedProducts() } returns exampleProducts
        every { repository.selectProductById(id = exampleProduct.id) } returns exampleProduct

        // when
        val presenter = ShoppingPresenter(
            view = view,
            repository = repository
        )

        presenter.addToRecentViewedProduct(exampleProduct.id)

        // then
        val expected = listOf(exampleProduct.toUiModel()) + exampleProducts.map { it.toUiModel() }

        verify { view.refreshRecentViewedProductsView(products = expected) }
    }
}
