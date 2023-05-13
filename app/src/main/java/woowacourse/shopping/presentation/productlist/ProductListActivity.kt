package woowacourse.shopping.presentation.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.product.MockProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductIdDbAdapter
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding

    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter

    private val recentProductRepository: RecentProductIdRepository by lazy {
        RecentProductIdDbAdapter(RecentProductDbHelper(this))
    }

    private val presenter: ProductListPresenter by lazy {
        ProductListPresenter(this, MockProductRepository, recentProductRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarProductList.toolbar)
    }

    override fun onStart() {
        super.onStart()
        presenter.updateRecentProducts()
    }

    override fun initProductModels(productModels: List<ProductModel>) {
        productListAdapter = ProductListAdapter(
            products = productModels,
            showMoreProductItem = ::showMoreProductItems,
            showProductDetail = ::productClick,
            recentProductAdapter = recentProductAdapter,
        )

        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.recyclerProduct.layoutManager = layoutManager.apply {
            spanSizeLookup = ProductListSpanSizeLookup(productListAdapter::getItemViewType)
        }

        binding.recyclerProduct.adapter = productListAdapter
    }

    override fun setProductModels(productModels: List<ProductModel>) {
        productListAdapter.setItems(productModels)
    }

    override fun initRecentProductModels(productModels: List<ProductModel>) {
        recentProductAdapter = RecentProductAdapter(productModels, ::productClick)
    }

    override fun setRecentProductModels(productModels: List<ProductModel>) {
        recentProductAdapter.setItems(productModels)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_cart -> startActivity(CartActivity.getIntent(this))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showMoreProductItems() {
        presenter.updateProducts()
    }

    private fun productClick(productModel: ProductModel) {
        presenter.saveRecentProductId(productModel.id)
        showProductDetail(productModel)
    }

    private fun showProductDetail(productModel: ProductModel) {
        startActivity(ProductDetailActivity.getIntent(this, productModel))
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
