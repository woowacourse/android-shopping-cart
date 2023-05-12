package woowacourse.shopping.view.productcatalogue

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.RecentProductDBHelper
import woowacourse.shopping.databinding.ActivityProductCatalogueBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.view.productdetail.ProductDetailActivity
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductCatalogueActivity : AppCompatActivity(), ProductCatalogueContract.View {
    override lateinit var presenter: ProductCatalogueContract.Presenter
    private lateinit var adapter: MainProductCatalogueAdapter

    private var _binding: ActivityProductCatalogueBinding? = null
    private val binding
        get() = _binding!!

    override fun onResume() {
        super.onResume()

        val db = RecentProductDBHelper(this).writableDatabase
        val recentProducts = presenter.getRecentProducts(db)
        db.close()

        adapter.update(recentProducts)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_product_catalogue)

        setSupportActionBar(binding.tbProductCatalogue)
        setPresenter()
        setAdapter()
        setViewSettings()
    }

    private fun setPresenter() {
        presenter = ProductCataloguePresenter(this)
    }

    override fun showProductDetailPage(): (ProductUIModel) -> Unit = {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, it)
        startActivity(intent)
    }

    private fun setAdapter() {
        val db = RecentProductDBHelper(this).writableDatabase

        adapter = MainProductCatalogueAdapter(
            presenter.getMainProducts(),
            presenter.getRecentProducts(db),
            showProductDetailPage()
        )

        db.close()
    }

    private fun setViewSettings() {
        val gridLayoutManager = GridLayoutManager(binding.root.context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) return 2
                return 1
            }
        }
        binding.rvProductCatalogue.layoutManager = gridLayoutManager
        binding.rvProductCatalogue.adapter = adapter
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
