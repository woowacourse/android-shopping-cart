package woowacourse.shopping.view.shoppingmain

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.RecentProductDBHelper
import woowacourse.shopping.databinding.ActivityShoppingMainBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.view.productdetail.ProductDetailActivity
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ShoppingMainActivity : AppCompatActivity(), ShoppingMainContract.View {
    override lateinit var presenter: ShoppingMainContract.Presenter
    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter

    private var _binding: ActivityShoppingMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_main)

        setSupportActionBar(binding.tbProductCatalogue)
        setPresenter()
        setAdapters()
        setViewSettings()
    }

    override fun onResume() {
        super.onResume()

        val db = RecentProductDBHelper(this).writableDatabase
        val recentProducts = presenter.getRecentProducts(db)

        recentProductAdapter.update(recentProducts)
    }

    private fun setPresenter() {
        presenter = ShoppingMainPresenter(this)
    }

    private fun setAdapters() {
        val productAdapter = ProductAdapter(
            presenter.getMainProducts(),
            showProductDetailPage()
        )
        recentProductAdapter = RecentProductAdapter(
            emptyList(),
            showProductDetailPage()
        )
        val recentProductWrapperAdapter = RecentProductWrapperAdapter(
            recentProductAdapter
        )

        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        concatAdapter = ConcatAdapter(config, recentProductWrapperAdapter, productAdapter)
    }

    override fun showProductDetailPage(): (ProductUIModel) -> Unit = {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, it)
        startActivity(intent)
    }

    private fun setViewSettings() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    ProductAdapter.VIEW_TYPE -> 1
                    RecentProductWrapperAdapter.VIEW_TYPE -> 2
                    else -> 2
                }
            }
        }
        binding.rvProductCatalogue.adapter = concatAdapter
        binding.rvProductCatalogue.layoutManager = gridLayoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_catalogue, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(ShoppingCartActivity.intent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
