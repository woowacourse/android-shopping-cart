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
import woowacourse.shopping.presentation.model.ProductViewType
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding

    private lateinit var productListAdapter: ProductListAdapter

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
        initRecyclerView()
        setSupportActionBar(binding.toolbarProductList.toolbar)
    }

    private fun initRecyclerView() {
        initProductListAdapter()
        presenter.loadProducts()
        setLayoutManager()
    }

    override fun onStart() {
        super.onStart()
        presenter.loadRecentProducts()
    }

    private fun initProductListAdapter(
//        recentProductModels: List<ProductModel>,
//        productModels: List<ProductModel>,
    ) {
        productListAdapter = ProductListAdapter(
            productItems = combineProductViewItems(listOf(), listOf()),
            showMoreProductItem = presenter::loadProducts,
            showProductDetail = ::productClick,
        )

        binding.recyclerProduct.adapter = productListAdapter
    }

    override fun setProductModels(productModels: List<ProductModel>) {
        productListAdapter.setProductItems(productModels)
    }

    private fun combineProductViewItems(
        recentProductModels: List<ProductModel>,
        productModels: List<ProductModel>,
    ): List<ProductViewType> {
        val productItems =
            listOf(ProductViewType.RecentProductModels(recentProductModels)) +
                productModels.map { ProductViewType.ProductItem(it) } + ProductViewType.MoreItem
        return productItems
    }

    override fun setRecentProductModels(productModels: List<ProductModel>) {
        productListAdapter.setRecentProductsItems(productModels)
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

    private fun productClick(productModel: ProductModel) {
        presenter.saveRecentProductId(productModel.id)
        showProductDetail(productModel)
    }

    private fun showProductDetail(productModel: ProductModel) {
        startActivity(ProductDetailActivity.getIntent(this, productModel))
    }

    private fun setLayoutManager() {
        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.recyclerProduct.layoutManager = layoutManager.apply {
            spanSizeLookup = GridLayoutSizeManager(productListAdapter::getItemViewType)
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
