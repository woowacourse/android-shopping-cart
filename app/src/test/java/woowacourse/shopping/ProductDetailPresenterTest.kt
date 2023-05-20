// package woowacourse.shopping
//
// import io.mockk.mockk
// import io.mockk.verify
// import org.junit.Before
// import org.junit.Test
// import woowacourse.shopping.database.ShoppingRepository
// import woowacourse.shopping.productdetail.ProductDetailContract
// import woowacourse.shopping.util.toUiModel
//
// class ProductDetailPresenterTest {
//
//    private lateinit var view: ProductDetailContract.View
//    private lateinit var repository: ShoppingRepository
//
//    @Before
//    fun setUp() {
//        view = mockk(relaxed = true)
//        repository = mockk(relaxed = true)
//    }
//
//    @Test
//    fun `프레젠터가 생성될 때 주입받은 상품에 대한 정보를 가지고 화면을 초기화한다`() {
//        // when
//        ProductDetailPresenter(
//            view = view,
//            product = Product(id = 1).toUiModel(),
//            repository = repository
//        )
//
//        // then
//        val expected = Product(id = 1).toUiModel()
//        verify { view.setUpProductDetailView(expected, any()) }
//    }
//
//    @Test
//    fun `프레젠터가 생성될 때 마지막으로 본 상품이 존재한다면 마지막으로 본 상품에 대한 뷰를 초기화한다`() {
//        // given
//        val latestViewedProduct = Product(name = "아메리카노")
//
//        // when
//        ProductDetailPresenter(
//            view = view,
//            repository = repository,
//            latestViewedProduct = latestViewedProduct.toUiModel()
//        )
//
//        // then
//        val expected = Product(name = "아메리카노").toUiModel()
//        verify { view.setUpLatestViewedProductView(expected) }
//    }
//
//    @Test
//    fun `장바구니에 상품을 추가하면 저장소를 통해서 상품 정보를 저장하고 장바구니 화면으로 이동한다`() {
//        val presenter = ProductDetailPresenter(
//            view = view,
//            product = Product().toUiModel(),
//            repository = repository
//        )
//
//        // when
//        presenter.addToCart()
//
//        // then
//        val expected = Product().toUiModel()
//        verify { repository.insertToShoppingCart(expected.id) }
//        verify { view.navigateToCartView() }
//    }
//
//    @Test
//    fun `장바구니에 담을 상품의 개수를 증가시키면 상품의 수를 나타내는 뷰를 갱신한다`() {
//        val presenter = ProductDetailPresenter(
//            view = view,
//            product = Product().toUiModel(),
//            repository = repository
//        )
//
//        // when
//        presenter.plusCartProductCount()
//
//        // then
//        val expected = 2
//        verify { view.setUpDialogProductCountView(expected) }
//    }
//
//    @Test
//    fun `장바구니에 담을 상품의 개수를 감소시키면 상품의 수를 나타내는 뷰를 갱신한다`() {
//        val presenter = ProductDetailPresenter(
//            view = view,
//            product = Product().toUiModel(),
//            repository = repository
//        )
//        presenter.plusCartProductCount()
//        presenter.plusCartProductCount()
//
//        // when
//        presenter.minusCartProductCount()
//
//        // then
//        val expected = 2
//        verify { view.setUpDialogProductCountView(expected) }
//    }
//
//    @Test
//    fun `장바구니에 담을 상품의 개수를 증가시키면 상품의 총 가격을 갱신한다`() {
//        val product = Product().toUiModel()
//        val presenter = ProductDetailPresenter(
//            view = view,
//            product = product,
//            repository = repository
//        )
//
//        // when
//        presenter.plusCartProductCount()
//
//        // then
//        val expectedCount = 2
//        val expected = expectedCount * product.price
//        verify { view.setUpDialogTotalPriceView(expected) }
//    }
//
//    @Test
//    fun `장바구니에 담을 상품의 개수를 감소시키면 상품의 총 가격을 갱신한다`() {
//        val product = Product().toUiModel()
//        val presenter = ProductDetailPresenter(
//            view = view,
//            product = product,
//            repository = repository
//        )
//        presenter.plusCartProductCount()
//        presenter.plusCartProductCount()
//        presenter.plusCartProductCount()
//        presenter.plusCartProductCount()
//
//        // when
//        presenter.minusCartProductCount()
//
//        // then
//        val expectedCount = 3
//        val expectedPrice = expectedCount * product.price
//        verify { view.setUpDialogTotalPriceView(expectedPrice) }
//    }
// }
