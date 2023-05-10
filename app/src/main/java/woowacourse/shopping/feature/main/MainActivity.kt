package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.data.RecentProductRepositoryImpl
import woowacourse.shopping.data.sql.recent.RecentDao
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.detail.DetailActivity
import woowacourse.shopping.feature.main.product.MainProductAdapter
import woowacourse.shopping.feature.main.product.MainProductItemModel
import woowacourse.shopping.feature.main.recent.RecentAdapter
import woowacourse.shopping.feature.main.recent.RecentProductItemModel
import woowacourse.shopping.feature.main.recent.RecentWrapperAdapter

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var mainProductAdapter: MainProductAdapter
    private lateinit var recentWrapperAdapter: RecentWrapperAdapter
    private lateinit var recentAdapter: RecentAdapter

    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        ConcatAdapter(config, recentWrapperAdapter, mainProductAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = MainPresenter(
            this,
            ProductMockRepository(),
            RecentProductRepositoryImpl(RecentDao(this))
        )

        mainProductAdapter = MainProductAdapter(listOf())
        recentAdapter = RecentAdapter(listOf())
        recentWrapperAdapter = RecentWrapperAdapter(recentAdapter)

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    RecentWrapperAdapter.VIEW_TYPE -> 2
                    MainProductAdapter.VIEW_TYPE -> 1
                    else -> 2
                }
            }
        }

        binding.productRv.layoutManager = layoutManager
        binding.productRv.adapter = concatAdapter
        presenter.loadProducts()
        presenter.loadRecent()
    }

    override fun showCartScreen() {
        TODO("Not yet implemented")
    }

    override fun showProductDetailScreen(position: Int) {
        val product = mainProductAdapter.items[position].product
        startActivity(DetailActivity.getIntent(this, product))
    }

    override fun addProducts(products: List<MainProductItemModel>) {
        mainProductAdapter.addItems(products)
    }

    override fun updateRecent(recent: List<RecentProductItemModel>) {
        recentAdapter.setItems(recent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        recentWrapperAdapter.onSaveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recentWrapperAdapter.onRestoreState(savedInstanceState)
    }
}
