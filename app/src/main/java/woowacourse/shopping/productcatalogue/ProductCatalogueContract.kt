package woowacourse.shopping.productcatalogue

import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

interface ProductCatalogueContract {
    interface View {
        fun attachNewProducts(loadedNewProducts: List<ProductUIModel>)
        fun setRecentProductList(recentProducts: List<RecentProductUIModel>)
        fun initProductList(recentProducts: List<RecentProductUIModel>)
        fun setCartCountCircle(count: Int)
        fun setGridLayoutManager(productsSize: Int)
    }

    interface Presenter {
        fun fetchRecentProduct()
        fun fetchMoreProducts(unitSize: Int, page: Int)
        fun fetchCartCount()
        fun getProductCount(product: ProductUIModel): Int
        fun decreaseCartProductCount(cartProduct: CartProductUIModel, count: Int)
        fun increaseCartProductCount(cartProduct: CartProductUIModel, count: Int)
        fun fetchProductsSizeForUpdateLayoutManager()
    }
}
