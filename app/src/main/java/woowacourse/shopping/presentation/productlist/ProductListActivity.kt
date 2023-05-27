package woowacourse.shopping.presentation.productlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbDao
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductMockServer
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductIdDbAdapter
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.ProductViewType
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.product.ProductClickListener
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var productListAdapter: ProductListAdapter

    private val presenter: ProductListPresenter by lazy {
        val productRepository = ProductRepositoryImpl(ProductMockServer().url)
        val recentProductRepository = RecentProductIdDbAdapter(RecentProductDbHelper(this))
        val cartProductRepository =
            CartRepositoryImpl(CartDbDao(CartDbHelper(this)), productRepository)

        ProductListPresenter(this, cartProductRepository, recentProductRepository)
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
        setLayoutManager()
        presenter.loadProducts()
    }

    override fun onStart() {
        super.onStart()
        presenter.loadRecentProducts()
    }

    private fun initProductListAdapter() {
        val productClickListener = object : ProductClickListener {
            override fun onAddClick(cartProductModel: CartProductModel) {
                presenter.addCartProductCount(cartProductModel)
            }

            override fun onRemoveClick(cartProductModel: CartProductModel) {
                presenter.subCartProductCount(cartProductModel)
            }

            override fun onItemClick(productModel: ProductModel) {
                productClick(productModel)
            }
        }

        productListAdapter = ProductListAdapter(
            productItems = listOf<ProductViewType.ProductItem>() + ProductViewType.MoreItem,
            showMoreProductItem = presenter::loadProducts,
            productClickListener = productClickListener,
        )

        binding.recyclerProduct.adapter = productListAdapter
    }

    override fun setProductModels(cartProductModels: List<CartProductModel>) {
        productListAdapter.setProductItems(
            cartProductModels.map { ProductViewType.ProductItem(it) },
        )
    }

    override fun setRecentProductModels(productModels: List<ProductModel>) {
        productListAdapter.setRecentProductsItems(productModels)
    }

    override fun setCartCount(count: Int) {
        binding.textCartCountProductList.text = count.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_cart -> {
                startActivity(CartActivity.getIntent(this))
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun productClick(productModel: ProductModel) {
        presenter.saveRecentProductId(productModel.id)
        showProductDetail(productModel.id)
    }

    private fun showProductDetail(productId: Long) {
        val recentProductId = productListAdapter.getRecentFirstProduct()
        startActivity(
            ProductDetailActivity.getIntent(this, productId, recentProductId),
        )
    }

    private fun setLayoutManager() {
        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.recyclerProduct.layoutManager = layoutManager.apply {
            spanSizeLookup = GridLayoutSizeManager(productListAdapter::getItemViewType)
        }
    }

    override fun replaceProductModel(cartProductModel: CartProductModel) {
        productListAdapter.replaceProductItem(ProductViewType.ProductItem(cartProductModel))
    }

    companion object {
        private const val SPAN_COUNT = 2
        fun getIntent(context: Context): Intent {
            return Intent(context, ProductListActivity::class.java)
        }
    }
}
