package woowacourse.shopping.shopping.contract.presenter

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.shopping.ProductItem
import woowacourse.shopping.shopping.ProductReadMore
import woowacourse.shopping.shopping.ProductsItemType
import woowacourse.shopping.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ProductRepository
) : ShoppingContract.Presenter {
    private val productsData: MutableList<ProductsItemType> = mutableListOf()

    override fun setUpProducts() {
        productsData += repository.getAll().map { product: Product ->
            ProductItem(product.toUIModel())
        }
        view.setProducts(productsData.plus(ProductReadMore))
    }

    override fun fetchMoreProducts() {
        productsData += repository.getAll().map { product: Product ->
            ProductItem(product.toUIModel())
        }
        view.addProducts(productsData.plus(ProductReadMore))
    }

    override fun navigateToItemDetail(data: ProductsItemType) {
        view.navigateToProductDetail(data)
    }
}
