package woowacourse.shopping.view.productlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
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
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ProductListPresenter(this, ProductMockRepository, RecentViewedDbRepository(this))
        presenter.fetchProducts()
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchProducts()
    }

    override fun showProducts(recentViewedProducts: List<ProductModel>, products: List<ProductModel>) {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recentViewedProducts.isNotEmpty() && position == 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.gridProducts.layoutManager = gridLayoutManager
        binding.gridProducts.adapter = ProductListAdapter(recentViewedProducts, products) {
            onClick(it)
        }
    }

    private fun onClick(product: ProductModel) {
        val intent = ProductDetailActivity.newIntent(binding.root.context, product)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
