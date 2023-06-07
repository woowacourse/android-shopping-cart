package woowacourse.shopping.feature.main

import com.example.data.repository.CartRepository
import com.example.data.repository.ProductRepository
import com.example.data.repository.RecentProductRepository
import com.example.domain.CartProduct
import com.example.domain.Product
import com.example.domain.RecentProducts
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.feature.model.mapper.toCartUi
import woowacourse.shopping.feature.model.mapper.toUi

internal class MainPresenterTest {

    private lateinit var view: MainContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: MainContract.Presenter

    @Before
    fun setup() {
        view = mockk()
        productRepository = mockk()
        recentProductRepository = mockk()
        cartRepository = mockk()

        every { productRepository.requestAll() } returns products

        presenter = MainPresenter(view, productRepository, recentProductRepository, cartRepository)
    }

    @Test
    fun `초기 데이터를 받아와 뷰에 보여준다`() {
        // given
        val recentProducts = RecentProducts(products.toMutableList())
        every { recentProductRepository.getRecentProducts() } returns recentProducts

        val cartProducts = listOf(cartProduct)
        every { cartRepository.getAll() } returns cartProducts

        justRun { view.setInitialProducts(any(), any()) }

        // when
        presenter.loadInitialData()

        // then
        verify { view.setInitialProducts(any(), any()) }
    }

    @Test
    fun `더보기 버튼을 클릭하면 데이터가 추가된다`() {
        // given
        val recentProducts = RecentProducts(products.toMutableList())
        every { recentProductRepository.getRecentProducts() } returns recentProducts

        val cartProducts = listOf(cartProduct)
        every { cartRepository.getAll() } returns cartProducts

        justRun { view.setInitialProducts(any(), any()) }
        justRun { view.addProducts(any()) }

        presenter.loadInitialData()

        // when
        presenter.loadMoreProducts()

        // then
        verify { view.addProducts(any()) }
    }

    @Test
    fun `클릭한 상품을 최근 본 상품으로 저장한다`() {
        // given
        every { recentProductRepository.getLastProduct() } returns product
        justRun { recentProductRepository.addColumn(product) }
        justRun { view.startActivity(product.toCartUi(), product.toCartUi()) }

        // when
        presenter.saveRecentProduct(product.toCartUi())

        // then
        verify { view.startActivity(product.toCartUi(), product.toCartUi()) }
    }

    @Test
    fun `장바구니에 있는 상품의 경우 +버튼을 누르면 상품의 개수를 증가시키고 뷰에 보여준다`() {
        // given
        val cartProduct = CartProduct(
            1,
            "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019",
            "헤어지자 말해요",
            100,
            3,
        )
        val newCartProduct = cartProduct.updateCount(cartProduct.count + 1)
        every { cartRepository.findProductById(product.id) } returns cartProduct
        justRun { cartRepository.updateColumn(newCartProduct) }
        justRun { view.setProduct(newCartProduct.toUi()) }

        // when
        presenter.updateProductCount(product.toCartUi(), true)

        // then
        verify { view.setProduct(newCartProduct.toUi()) }
    }

    @Test
    fun `장바구니에 있는 상품의 경우 -버튼을 누르면 상품의 개수를 감소시키고 뷰에 보여준다`() {
        // given
        val cartProduct = CartProduct(
            1,
            "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019",
            "헤어지자 말해요",
            100,
            3,
        )
        val newCartProduct = cartProduct.updateCount(cartProduct.count - 1)
        every { cartRepository.findProductById(product.id) } returns cartProduct
        justRun { cartRepository.updateColumn(newCartProduct) }
        justRun { view.setProduct(newCartProduct.toUi()) }

        // when
        presenter.updateProductCount(product.toCartUi(), false)

        // then
        verify { view.setProduct(newCartProduct.toUi()) }
    }

    @Test
    fun `장바구니에 없는 경우 장바구니에 아이템을 추가한다`() {
        // given
        every { cartRepository.findProductById(product.id) } returns null
        justRun { cartRepository.addColumn(product, 1) }

        // when
        presenter.updateProductCount(product.toCartUi(), true)

        // then
        verify { cartRepository.addColumn(product, 1) }
    }

    companion object {
        private val product = Product(
            1,
            "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019",
            "헤어지자 말해요",
            100,
        )

        private val products = MutableList(25) {
            Product(
                it + 1,
                "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019",
                "헤어지자 말해요 $it",
                100 + it,
            )
        }

        private val cartProduct = CartProduct(
            1,
            "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019",
            "헤어지자 말해요",
            100,
            3,
        )
    }
}
