package woowacourse.shopping.productcatalogue

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityProductCatalogueBinding
import woowacourse.shopping.datas.ProductDataRepository
import woowacourse.shopping.datas.RecentProductDBHelper
import woowacourse.shopping.datas.RecentProductDBRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.productcatalogue.list.MainProductCatalogueAdapter
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.uimodel.ProductUIModel

class ProductCatalogueActivity : AppCompatActivity(), ProductCatalogueContract.View {
    private lateinit var binding: ActivityProductCatalogueBinding
    override lateinit var presenter: ProductCatalogueContract.Presenter
    private val recentDataRepository: RecentRepository by lazy {
        RecentProductDBRepository(RecentProductDBHelper(this).writableDatabase)
    }
    private val adapter: MainProductCatalogueAdapter by lazy {
        MainProductCatalogueAdapter(
            recentDataRepository,
            presenter.productOnClick(),
            presenter.readMoreOnClick(),
        )
    }

    override fun showProductDetailPage(productUIModel: ProductUIModel) {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, productUIModel)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_catalogue)

        setSupportActionBar(binding.tbProductCatalogue)

        presenter = ProductCataloguePresenter(this)

        binding.rvProductCatalogue.adapter = adapter
        val gridLayoutManager = GridLayoutManager(binding.root.context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) return 2
                if (ProductDataRepository.products.size + 1 == position) return 2
                return 1
            }
        }

        binding.rvProductCatalogue.layoutManager = gridLayoutManager

        notifyDataChanged()
    }

    override fun notifyDataChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_catalogue, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(CartActivity.intent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        adapter.recentAdapter.notifyDataSetChanged()
        adapter.setRecentProductsVisibility(binding.clProductCatalogue)
        super.onResume()
    }
}
