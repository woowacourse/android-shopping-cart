package woowacourse.shopping.shopping.contract.presenter

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository
import com.example.domain.model.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.shopping.ProductItem
import woowacourse.shopping.shopping.ProductReadMore
import woowacourse.shopping.shopping.ProductsItemType
import woowacourse.shopping.shopping.RecentProductsItem
import woowacourse.shopping.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ProductRepository,
    private val recentRepository: RecentRepository
) : ShoppingContract.Presenter {
    private var productsData: MutableList<ProductsItemType> = mutableListOf()

    override fun setUpProducts() {
        val recentProductsData = RecentProductsItem(
            recentRepository.getRecent(10).map { it.toUIModel() }
        )

        if (recentProductsData.product.isNotEmpty()) {
            productsData = mutableListOf(recentProductsData)
        }

        productsData += repository.getNext(20)
            .map { product: Product -> ProductItem(product.toUIModel()) }
        view.setProducts(productsData.plus(ProductReadMore))
    }

    override fun fetchMoreProducts() {
        productsData += repository.getNext(20)
            .map { ProductItem(it.toUIModel()) }
        view.addProducts(productsData.plus(ProductReadMore))
    }

    override fun navigateToItemDetail(data: ProductUIModel) {
        view.navigateToProductDetail(data)
    }
}
