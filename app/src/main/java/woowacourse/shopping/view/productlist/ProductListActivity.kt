package woowacourse.shopping.view.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var presenter: ProductListContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setPresenter()
        setActionBar()
    }

    // + 버튼을 달아줘야함
    // + 누르면 개수 선택 뷰 나와야함
    // 근데 장바구니에 담겨 있으면 해당 개수 이미 개수 선택 뷰로 보여주고 있어야함
    // 개수 선택 뷰 누를때마다 찐으로 디비까지 개수 담겨야함
    // 툴바까지 해줘야
    //

    override fun onResume() {
        super.onResume()
        presenter.fetchProducts()
    }

    private fun setBinding() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
    }

    private fun setPresenter() {
        presenter =
            ProductListPresenter(this, ProductMockRepository, RecentViewedDbRepository(this), CartDbRepository(this))
    }

    private fun setActionBar() {
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
            CartDbRepository(this),
            fun(product: ProductModel) { showProductDetail(product) },
            fun() { presenter.showMoreProducts() },
        )
    }

    override fun notifyAddProducts(position: Int, size: Int) {
        binding.gridProducts.adapter?.notifyItemRangeInserted(position, size)
    }

    private fun showProductDetail(product: ProductModel) {
        val intent = ProductDetailActivity.newIntent(this, product, false)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
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
