package woowacourse.shopping.shoppingcart

import model.Page
import model.PageRule
import model.ShoppingCart
import model.ShoppingCartProduct
import model.ShowingCartProductsPageRule
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ShoppingCartProductUiModel
import woowacourse.shopping.util.toDomainModel
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository,
    private val pageRuleImpl: PageRule = ShowingCartProductsPageRule,
) : ShoppingCartContract.Presenter {

    private var numberOfReadShoppingCartProduct: Int = 0
    private var shoppingCartProducts: MutableList<ShoppingCartProduct> = mutableListOf()
    private var currentPage = Page()
    private val endPage: Page
        get() = pageRuleImpl.getPageOfEnd(
            totalProductsSize = shoppingCartProducts.size
        )

    private val showingProducts: List<ShoppingCartProductUiModel>
        get() = pageRuleImpl.getProductsOfPage(
            products = shoppingCartProducts,
            page = currentPage
        ).map { it.toUiModel() }

    private fun selectShoppingCartProducts(): List<ShoppingCartProduct> {
        val products = repository.selectShoppingCartProducts(
            from = numberOfReadShoppingCartProduct,
            count = COUNT_TO_READ
        )

        numberOfReadShoppingCartProduct += products.size

        return products
    }

    override fun loadShoppingCartProducts() {
        val products = selectShoppingCartProducts()
        shoppingCartProducts.addAll(products)

        view.setUpShoppingCartView(
            products = shoppingCartProducts.map { it.toUiModel() },
            currentPage = currentPage.value
        )
    }

    override fun removeShoppingCartProduct(product: ShoppingCartProductUiModel) {
        repository.deleteFromShoppingCart(product.id)
        shoppingCartProducts.remove(product.toDomainModel())
        view.refreshShoppingCartProductView(showingProducts)
    }

    // TODO: ShoppingCart의 역할이 애매모호해짐
    override fun plusShoppingCartProductCount(product: ShoppingCartProductUiModel) {
        val indexOfProduct = showingProducts.indexOfFirst { it.id == product.id }
        val shoppingCartProduct = product.toDomainModel().plusCount()
        shoppingCartProducts.removeAt(indexOfProduct)
        shoppingCartProducts.add(indexOfProduct, shoppingCartProduct)

        repository.insertToShoppingCart(
            id = shoppingCartProduct.product.id,
            count = shoppingCartProduct.count.value
        )
        view.refreshShoppingCartProductView(
            products = showingProducts
        )
    }

    override fun minusShoppingCartProductCount(product: ShoppingCartProductUiModel) {
        val indexOfProduct = showingProducts.indexOfFirst { it.id == product.id }
        val shoppingCartProduct = product.toDomainModel().minusCount()
        shoppingCartProducts.removeAt(indexOfProduct)
        shoppingCartProducts.add(indexOfProduct, shoppingCartProduct)

        repository.insertToShoppingCart(
            id = shoppingCartProduct.product.id,
            count = shoppingCartProduct.count.value
        )
        view.refreshShoppingCartProductView(
            products = showingProducts
        )
    }

    override fun calcTotalPrice() {
        val totalPrice = ShoppingCart(
            products = showingProducts.map { it.toDomainModel() }
        ).totalPrice

        view.setUpTextTotalPriceView(price = totalPrice)
    }

    // TODO: 페이지 관련 테스트
    // TODO: ShoppingCart의 역할이 애매모호해짐
    override fun changeProductSelectedState(
        product: ShoppingCartProductUiModel,
        isSelected: Boolean,
    ) {
        val indexOfProduct = showingProducts.indexOfFirst { it == product }
        val shoppingCartProduct = product.toDomainModel()
            .setSelectedState(isSelected)

        shoppingCartProducts.removeAt(indexOfProduct)
        shoppingCartProducts.add(indexOfProduct, shoppingCartProduct)
        view.refreshShoppingCartProductView(showingProducts)
    }

    override fun changeProductsSelectedState(checked: Boolean) {
        shoppingCartProducts = shoppingCartProducts.map { it.setSelectedState(checked) }
            .toMutableList()

        view.refreshShoppingCartProductView(products = showingProducts)
    }

    override fun moveToNextPage() {
        if (currentPage == endPage) {
            val products = selectShoppingCartProducts()

            shoppingCartProducts.addAll(products)

            if (products.isEmpty()) {
                return view.showMessageReachedEndPage()
            }
        }
        currentPage = currentPage.next()
        changePage()
    }

    override fun moveToPrevPage() {
        currentPage = currentPage.prev()
        changePage()
    }

    private fun changePage() {
        view.refreshShoppingCartProductView(
            products = showingProducts
        )
        view.setUpTextPageNumber(currentPage.value)
    }

    companion object {
        private const val COUNT_TO_READ = 3
    }
}
