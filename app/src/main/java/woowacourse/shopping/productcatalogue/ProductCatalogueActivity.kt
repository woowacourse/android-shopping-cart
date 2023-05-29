package woowacourse.shopping.productcatalogue

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.shopping.domain.CartRepository
import com.shopping.domain.ProductRepository
import com.shopping.domain.RecentRepository
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityProductCatalogueBinding
import woowacourse.shopping.datas.CartDBHelper
import woowacourse.shopping.datas.CartRepositoryImpl
import woowacourse.shopping.datas.MockServerProductRepositoryImpl
import woowacourse.shopping.datas.RecentProductDBHelper
import woowacourse.shopping.datas.RecentRepositoryImpl
import woowacourse.shopping.productcatalogue.list.MainProductCatalogueAdapter
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductCatalogueActivity :
    AppCompatActivity(),
    ProductCatalogueContract.View,
    ProductClickListener,
    ProductCountClickListener {
    private lateinit var binding: ActivityProductCatalogueBinding
    private lateinit var presenter: ProductCatalogueContract.Presenter
    private val adapter: MainProductCatalogueAdapter by lazy {
        MainProductCatalogueAdapter(
            this,
            this,
            ::readMore,
            ::getProductCount,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_catalogue)

        setSupportActionBar(binding.tbProductCatalogue)

        val productRepositoryServerImpl: ProductRepository = MockServerProductRepositoryImpl()
        val recentDataRepository: RecentRepository =
            RecentRepositoryImpl(RecentProductDBHelper(this).writableDatabase)
        val cartRepositoryImpl: CartRepository =
            CartRepositoryImpl(CartDBHelper(this).writableDatabase)

        presenter = ProductCataloguePresenter(
            this,
            productRepositoryServerImpl,
            recentDataRepository,
            cartRepositoryImpl
        )

        binding.rvProductCatalogue.adapter = adapter
        presenter.getRecentProduct()
        presenter.getNewProducts(20, 1)

        presenter.getSpanSize()

        presenter.updateCartCount()

        binding.ivCart.setOnClickListener {
            startActivity(CartActivity.getIntent(this))
        }
    }

    override fun setGridLayoutManager(productsSize: Int) {
        val gridLayoutManager = GridLayoutManager(binding.root.context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) return 2
                if (productsSize + 1 == position) return 2
                return 1
            }
        }
        binding.rvProductCatalogue.layoutManager = gridLayoutManager
    }

    private fun readMore(unitSize: Int, page: Int) {
        presenter.getNewProducts(unitSize, page)
    }

    override fun setRecentProductList(recentProducts: List<RecentProductUIModel>) {
        adapter.updateRecentProducts(recentProducts)
    }

    override fun updateProductList(recentProducts: List<RecentProductUIModel>) {
        adapter.initRecentAdapterData(recentProducts)
    }

    override fun setCartCountCircle(count: Int) {
        binding.tvCartCount.text = count.toString()
    }

    override fun attachNewProducts(loadedNewProducts: List<ProductUIModel>) {
        runOnUiThread { adapter.updateProducts(loadedNewProducts) }
    }

    override fun onProductClick(productUIModel: ProductUIModel) {
        val intent = ProductDetailActivity.getIntent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, productUIModel)
        startActivity(intent)
    }

    override fun onDownClicked(cartProduct: CartProductUIModel, countView: TextView) {
        val count = countView.text.toString().toInt() - 1
        presenter.decreaseCartProductCount(cartProduct, count)
        presenter.updateCartCount()
    }

    override fun onUpClicked(cartProduct: CartProductUIModel, countView: TextView) {
        val count = countView.text.toString().toInt() + 1
        presenter.increaseCartProductCount(cartProduct, count)
        presenter.updateCartCount()
    }

    private fun getProductCount(product: ProductUIModel): Int {
        return presenter.getProductCount(product)
    }

    override fun onResume() {
        presenter.getRecentProduct()
        adapter.recentAdapter.notifyDataSetChanged()
        adapter.setRecentProductsVisibility(binding.clProductCatalogue)
        presenter.updateCartCount()
        super.onResume()
    }
}
