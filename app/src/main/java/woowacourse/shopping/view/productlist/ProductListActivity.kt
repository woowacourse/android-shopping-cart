package woowacourse.shopping.view.productlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        presenter =
            ProductListPresenter(this, ProductMockRepository, RecentViewedDbRepository(this))
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        Log.d("test", "onResume viewedData 갱신")
        presenter.fetchProducts()
    }

    override fun showProducts(
        recentViewedProducts: List<ProductModel>,
        products: List<ProductModel>,
    ) {
        Log.d("test", "showProducts 들어와서 리사이클러뷰 달아줌")
        val gridLayoutManager = GridLayoutManagerWrapper(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val isHeader = recentViewedProducts.isNotEmpty() && position == 0
                val isFooter =
                    if (recentViewedProducts.isNotEmpty()) position == products.size + 1 else position == products.size
                return if (isHeader || isFooter) {
                    2
                } else {
                    1
                }
            }
        }
        binding.gridProducts.layoutManager = gridLayoutManager
        binding.gridProducts.adapter = ProductListAdapter(
            recentViewedProducts,
            products,
            object : ProductListAdapter.OnItemClick {
                override fun onProductClick(product: ProductModel) {
                    showProductDetail(product)
                }

                override fun onShowMoreClick() {
                    presenter.showMoreProducts()
                }
            },
        )
    }

    override fun notifyAddProducts(position: Int, size: Int) {
        binding.gridProducts.adapter?.notifyItemRangeInserted(position, size)
    }

    private fun showProductDetail(product: ProductModel) {
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
