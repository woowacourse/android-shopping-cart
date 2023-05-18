// package woowacourse.shopping
//
// import io.mockk.every
// import io.mockk.mockk
// import io.mockk.verify
// import model.ShoppingCartProduct
// import org.junit.Before
// import org.junit.Test
// import woowacourse.shopping.database.ShoppingRepository
// import woowacourse.shopping.shoppingcart.ShoppingCartContract
// import woowacourse.shopping.shoppingcart.ShoppingCartPresenter
// import woowacourse.shopping.util.toUiModel
//
// class ShoppingCartPresenterTest {
//
//    private lateinit var presenter: ShoppingCartContract.Presenter
//    private lateinit var view: ShoppingCartContract.View
//    private lateinit var repository: ShoppingRepository
//    private lateinit var products: List<ShoppingCartProduct>
//
//    @Before
//    fun setUp() {
//        view = mockk(relaxed = true)
//        repository = mockk(relaxed = true)
//        presenter = ShoppingCartPresenter(
//            view = view,
//            repository = repository,
//        )
//        products = listOf(
//            ShoppingCartProduct(name = "아메리카노"),
//            ShoppingCartProduct(name = "밀크티"),
//        )
//    }
//
//    @Test
//    fun `저장소에서 장바구니에 담긴 상품들을 받아와서 뷰를 초기화한다`() {
//        // given
//        every { repository.selectShoppingCartProducts(any(), any()) } returns products
//
//        // when
//        presenter.loadShoppingCartProducts()
//
//        // then
//        val expected = products.map { it.toUiModel() }
//        verify { view.setUpShoppingCartView(expected, currentPage.value) }
//    }
//
//    @Test
//    fun `상품을 삭제하면 저장소에게 삭제할 상품의 아이디를 전해준다`() {
//        // given
//        val product = ShoppingCartProduct(id = 1).toUiModel()
//
//        // when
//        presenter.removeShoppingCartProduct(product)
//
//        // then
//        verify { repository.deleteFromShoppingCart(id = 1) }
//    }
//
//    @Test
//    fun `상품의 개수를 추가시키면 저장소에 개수가 하나 증가한 상품의 데이터를 삽입한다`() {
//        // given
//        val product = ShoppingCartProduct(
//            id = 1,
//            count = 5
//        ).toUiModel()
//
//        // when
//        presenter.plusShoppingCartProductCount(product)
//
//        // then
//        verify { repository.insertToShoppingCart(id = 1, count = 6) }
//    }
//
//    @Test
//    fun `상품의 개수를 감소시키면 저장소에 개수가 하나 감소된 상품의 데이터를 삽입한다`() {
//        val product = ShoppingCartProduct(
//            id = 1,
//            count = 5
//        ).toUiModel()
//
//        // when
//        presenter.minusShoppingCartProductCount(product)
//
//        // then
//        verify { repository.insertToShoppingCart(id = 1, count = 4) }
//    }
//
//    @Test
//    fun `상품의 총 가격을 계산해서 가격을 나타내는 텍스트 뷰를 초기화한다`() {
//        // given
//        val products = listOf(
//            ShoppingCartProduct(price = 5000),
//            ShoppingCartProduct(price = 1000),
//            ShoppingCartProduct(price = 2000),
//        ).map { it.toUiModel() }
//
//        // when
//        presenter.calcTotalPrice()
//
//        // then
//        verify { view.setUpTextTotalPriceView(8000) }
//    }
// }
