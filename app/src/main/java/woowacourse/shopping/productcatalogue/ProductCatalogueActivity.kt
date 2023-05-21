package woowacourse.shopping.productcatalogue

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityProductCatalogueBinding
import woowacourse.shopping.datas.CartDBHelper
import woowacourse.shopping.datas.CartDBRepository
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.datas.ProductDataRepository
import woowacourse.shopping.datas.RecentProductDBHelper
import woowacourse.shopping.datas.RecentProductDBRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.productcatalogue.list.MainProductCatalogueAdapter
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductCatalogueActivity :
    AppCompatActivity(),
    ProductCatalogueContract.View,
    ProductClickListener {
    private lateinit var binding: ActivityProductCatalogueBinding
    private lateinit var presenter: ProductCatalogueContract.Presenter
    private val adapter: MainProductCatalogueAdapter by lazy {
        MainProductCatalogueAdapter(
            this,
            ::readMore,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_catalogue)

        setSupportActionBar(binding.tbProductCatalogue)

        val recentDataRepository: RecentRepository =
            RecentProductDBRepository(RecentProductDBHelper(this).writableDatabase)
        val cartDBRepository: CartRepository =
            CartDBRepository(CartDBHelper(this).writableDatabase)

        presenter = ProductCataloguePresenter(
            this,
            ProductDataRepository,
            recentDataRepository,
            cartDBRepository
        )

        presenter.getRecentProduct()

        binding.rvProductCatalogue.adapter = adapter

        val gridLayoutManager = GridLayoutManager(binding.root.context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) return 2
                if (ProductDataRepository.products.size + 1 == position) return 2
                return 1
            }
        }

        binding.rvProductCatalogue.layoutManager = gridLayoutManager

        notifyDataChanged()

        presenter.updateCartCount()

        binding.ivCart.setOnClickListener {
            startActivity(CartActivity.intent(this))
        }
    }

    private fun readMore(unitSize: Int, page: Int) {
        presenter.readMoreOnClick(unitSize, page)
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

    override fun notifyDataChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun onProductClick(productUIModel: ProductUIModel) {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, productUIModel)
        startActivity(intent)
    }

    override fun onResume() {
        presenter.getRecentProduct()
        adapter.recentAdapter.notifyDataSetChanged()
        adapter.setRecentProductsVisibility(binding.clProductCatalogue)
        presenter.updateCartCount()
        super.onResume()
    }
}
