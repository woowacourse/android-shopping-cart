package woowacourse.shopping.view.productcatalogue

class ProductCataloguePresenter(private val view: ProductCatalogueContract.View) :
    ProductCatalogueContract.Presenter {

    override fun setProductOnClick() {
        view.showProductDetailPage()
    }
}
