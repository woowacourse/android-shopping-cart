package woowacourse.shopping.ui.shopping

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDaoImpl
import woowacourse.shopping.data.datasource.product.LocalProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.recyclerview.layoutmanager.ShoppingGridLayoutManager
import woowacourse.shopping.ui.shopping.recyclerview.listener.EndScrollListener
import woowacourse.shopping.ui.shopping.recyclerview.product.ProductAdapter
import woowacourse.shopping.ui.shopping.recyclerview.recentproduct.RecentProductAdapter
import woowacourse.shopping.ui.shopping.recyclerview.recentproduct.RecentProductWrapperAdapter
import woowacourse.shopping.util.isolatedViewTypeConfig
import woowacourse.shopping.util.setOnSingleClickListener

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    override val presenter: ShoppingContract.Presenter by lazy {
        val shoppingDatabase = ShoppingDatabase(this)
        ShoppingPresenter(
            view = this,
            productRepository = ProductRepository(
                LocalProductDataSource(ProductDaoImpl(shoppingDatabase))
            ),
            recentProductRepository = RecentProductRepository(
                LocalRecentProductDataSource(RecentProductDaoImpl(shoppingDatabase))
            )
        )
    }
    private lateinit var binding: ActivityShoppingBinding

    private lateinit var recentProductWrapperAdapter: RecentProductWrapperAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        recentProductAdapter = RecentProductAdapter(presenter::inquiryRecentProductDetail)
        recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductAdapter)
        productAdapter = ProductAdapter(presenter::inquiryProductDetail)
        val concatAdapter =
            ConcatAdapter(isolatedViewTypeConfig, recentProductWrapperAdapter, productAdapter)

        binding.rvShopping.adapter = concatAdapter
        binding.rvShopping.layoutManager = ShoppingGridLayoutManager(concatAdapter, this)

        presenter.fetchRecentProducts()
        presenter.fetchProducts()

        binding.rvShopping.addOnScrollListener(EndScrollListener(presenter::fetchHasNext))
    }

    override fun updateProducts(products: List<UiProduct>) {
        productAdapter.submitList(products)
    }

    override fun updateRecentProducts(recentProducts: List<UiRecentProduct>) {
        recentProductAdapter.submitList(recentProducts)
    }

    override fun showProductDetail(product: UiProduct) {
        startActivity(ProductDetailActivity.getIntent(this, product))
    }

    override fun updateMoreButtonVisibility(isVisible: Boolean) {

    }

    private fun initClickListener() {
        binding.ivBasket.setOnSingleClickListener {
            startActivity(BasketActivity.getIntent(this))
        }
    }

    companion object {
        fun insertDummies(context: Context, size: Int) {
            (0 until size).forEach { id ->
                ProductDaoImpl(ShoppingDatabase(context)).add(
                    DataProduct(
                        id,
                        "name $id",
                        DataPrice(1000),
                        "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg"
                    )
                )
            }
        }
    }
}
