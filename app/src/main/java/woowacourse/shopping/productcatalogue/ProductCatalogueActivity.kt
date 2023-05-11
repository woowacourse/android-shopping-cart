package woowacourse.shopping.productcatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.MainProductCatalogueAdapter
import woowacourse.shopping.MainProductCatalogueUIModel
import woowacourse.shopping.ProductDBHelper
import woowacourse.shopping.ProductDBRepository
import woowacourse.shopping.ProductMockData
import woowacourse.shopping.ProductUIModel
import woowacourse.shopping.R
import woowacourse.shopping.RecentProductCatalogueUIModel
import woowacourse.shopping.databinding.ActivityProductCatalogueBinding
import woowacourse.shopping.productdetail.ProductDetailActivity

class ProductCatalogueActivity : AppCompatActivity(), ProductCatalogueContract.View {
    private lateinit var binding: ActivityProductCatalogueBinding
    override lateinit var presenter: ProductCatalogueContract.Presenter
    private val adapter: MainProductCatalogueAdapter = MainProductCatalogueAdapter(
        ProductMockData.mainProductMockData,
        ProductMockData.recentProductMockData,
        showProductDetailPage(),
    )

    override fun showProductDetailPage(): (ProductUIModel) -> Unit = {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, it)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val dbHelper = ProductDBHelper(this)
        val db = dbHelper.writableDatabase
        val repository = ProductDBRepository(db)
        val recentProducts = repository.getAll()

        adapter.update(
            RecentProductCatalogueUIModel(
                MainProductCatalogueUIModel(recentProducts)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_catalogue)
        presenter = ProductCataloguePresenter(this)

        binding.rvProductCatalogue.adapter = adapter
        val gridLayoutManager = GridLayoutManager(binding.root.context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) return 2
                return 1
            }
        }

        binding.rvProductCatalogue.layoutManager = gridLayoutManager

        adapter.notifyDataSetChanged()
    }
}
