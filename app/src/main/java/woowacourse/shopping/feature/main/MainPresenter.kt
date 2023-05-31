package woowacourse.shopping.feature.main

import com.example.data.repository.ProductRepository
import com.example.domain.Product
import woowacourse.shopping.data.datasource.cartdatasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.datasource.recentproductdatasource.RecentProductLocalDataSourceImpl
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.list.item.ProductView.RecentProductsItem
import woowacourse.shopping.feature.model.mapper.toCartUi
import woowacourse.shopping.feature.model.mapper.toProductDomain
import woowacourse.shopping.feature.model.mapper.toUi
import kotlin.properties.Delegates

class MainPresenter(
    val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductDb: RecentProductLocalDataSourceImpl,
    private val cartProductDb: CartLocalDataSourceImpl,
) : MainContract.Presenter {

    private val products: List<Product> = productRepository.requestAll()
    private var currentItemIndex by Delegates.notNull<Int>()

    override fun loadInitialData() {
        currentItemIndex = START_INDEX
        val products: MutableList<CartProductItem> =
            productRepository.requestAll().subList(currentItemIndex, ADD_SIZE).map { product ->
                product.toCartUi()
            }.toMutableList()
        currentItemIndex = ADD_SIZE

        val recentProducts: RecentProductsItem = recentProductDb.getRecentProducts().toUi()
        val cartProducts = cartProductDb.getAll().map { it.toUi() }

        cartProducts.forEach { cartProduct ->
            if (products.contains(cartProduct)) {
                val index = products.indexOf(cartProduct)
                products.remove(cartProduct)
                products.add(index, cartProduct)
            }
        }

        view.setInitialProducts(products, recentProducts)
    }

    override fun loadMoreProducts() {
        if (currentItemIndex >= products.lastIndex) return

        val addItems: List<Product>
        if (products.size < currentItemIndex + ADD_SIZE) {
            addItems = products.subList(currentItemIndex, products.size)
            currentItemIndex += products.lastIndex - currentItemIndex
        } else {
            addItems = products.subList(currentItemIndex, currentItemIndex + ADD_SIZE)
            currentItemIndex += ADD_SIZE
        }

        view.addProducts(addItems.map { it.toCartUi() })
    }

    override fun saveRecentProduct(product: CartProductItem) {
        val lastProduct = recentProductDb.getLastProduct() ?: return
        recentProductDb.addColumn(product.toProductDomain())
        view.startActivity(product, lastProduct.toCartUi())
    }

    override fun updateProductCount(product: CartProductItem, isPlus: Boolean) {
        val changeWidth = if (isPlus) 1 else -1

        cartProductDb.findProductById(product.id)?.let { cartProduct ->
            cartProduct.updateCount(cartProduct.count + changeWidth)
            cartProductDb.updateColumn(cartProduct)
            view.setProduct(cartProduct)
        } ?: cartProductDb.addColumn(product.toProductDomain())
    }

    override fun addProduct(product: CartProductItem) {
        cartProductDb.findProductById(product.id)
            ?: cartProductDb.addColumn(product.toProductDomain())
    }

    companion object {
        private const val ADD_SIZE = 20
        private const val START_INDEX = 0
    }
}
