package woowacourse.shopping.view.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.CartProductSqliteProductRepository
import woowacourse.shopping.data.ProductMockWebRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var presenter: ProductListContract.Presenter

    private var menuCount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setPresenter()
        setActionBar()
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchProducts()
    }

    private fun setBinding() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
    }

    private fun setPresenter() {
        presenter =
            ProductListPresenter(
                this,
                ProductMockWebRepository(),
                RecentViewedDbRepository(this),
                CartProductSqliteProductRepository(
                    CartDBHelper(this),
                ),
            )
    }

    private fun setActionBar() {
        setSupportActionBar(binding.listToolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun showProducts(
        recentViewedProducts: List<ProductModel>,
        products: List<ProductModel>,
    ) {
        val gridLayoutManager = GridLayoutManagerWrapper(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return presenter.calculateSpanSize(recentViewedProducts, position)
            }
        }
        binding.gridProducts.layoutManager = gridLayoutManager
        binding.gridProducts.adapter = ProductListAdapter(
            recentViewedProducts,
            products,
            fun(product: ProductModel) { showProductDetail(product) },
            fun() { presenter.showMoreProducts() },
            ::showCartItemsCountInMenu,
            fun(product: ProductModel) { presenter.addProductCount(product) },
            fun(product: ProductModel) { presenter.plusProductCount(product) },
            fun(product: ProductModel) { presenter.minusProductCount(product) },
        )
    }

    override fun notifyAddProducts(position: Int, size: Int) {
        binding.gridProducts.adapter?.notifyItemRangeInserted(position, size)
    }

    private fun showProductDetail(product: ProductModel) {
        val intent = ProductDetailActivity.newIntent(this, product, false)
        startActivity(intent)
    }

    private fun showCartItemsCountInMenu() {
        if (presenter.getCartItemsCount() <= 0) {
            menuCount?.visibility = View.GONE
        } else {
            menuCount?.visibility = View.VISIBLE
            menuCount?.text = presenter.getCartItemsCount().toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        menu?.findItem(R.id.cart)?.actionView?.let { view ->
            view.setOnClickListener { handleCartMenuClicked() }
            view.findViewById<TextView>(R.id.cart_icon_count)?.let { menuCount = it }
        }
        showCartItemsCountInMenu()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.handleNextStep(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun handleCartMenuClicked() {
        startActivity(CartActivity.newIntent(this))
    }
}
