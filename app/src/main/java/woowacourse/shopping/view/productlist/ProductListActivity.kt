package woowacourse.shopping.view.productlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
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
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_VIEWED) {
            val id = it.data?.getIntExtra("id", -1)
            presenter.updateRecentViewed(id ?: -1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)
        setUpPresenter()
        setUpActionBar()
        presenter.fetchProducts()
    }

    private fun setUpBinding() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
    }

    private fun setUpPresenter() {
        presenter =
            ProductListPresenter(this, ProductMockRepository, RecentViewedDbRepository(this), CartDbRepository(this))
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun showProducts(items: List<ProductListViewItem>) {
        val gridLayoutManager = GridLayoutManagerWrapper(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val isHeader = items[position].type == ProductListViewType.RECENT_VIEWED_ITEM
                val isFooter = items[position].type == ProductListViewType.SHOW_MORE_ITEM
                return if (isHeader || isFooter) {
                    HEADER_FOOTER_SPAN
                } else {
                    PRODUCT_ITEM_SPAN
                }
            }
        }
        binding.gridProducts.layoutManager = gridLayoutManager
        binding.gridProducts.adapter = ProductListAdapter(
            items,
            object : ProductListAdapter.OnItemClick {
                override fun onProductClick(product: ProductModel) {
                    showProductDetail(product)
                }

                override fun onShowMoreClick() {
                    presenter.showMoreProducts()
                }

                override fun onProductClickAddFirst(id: Int, count: Int) {
                    presenter.addToCartProducts(id, count)
                }

            },
        )
    }

    override fun notifyAddProducts(position: Int, size: Int) {
        binding.gridProducts.adapter?.notifyItemRangeInserted(position, size)
    }

    override fun notifyRecentViewedChanged() {
        binding.gridProducts.adapter?.notifyItemChanged(0)
    }

    private fun showProductDetail(product: ProductModel) {
        val intent = ProductDetailActivity.newIntent(binding.root.context, product)
        resultLauncher.launch(intent)
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
    companion object {
        private const val HEADER_FOOTER_SPAN = 2
        private const val PRODUCT_ITEM_SPAN = 1
        const val RESULT_VIEWED = 200
        const val ID = "id"
    }
}
