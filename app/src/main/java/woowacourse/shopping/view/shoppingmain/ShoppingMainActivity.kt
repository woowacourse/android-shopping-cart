package woowacourse.shopping.view.shoppingmain

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.repository.ProductMockRepository
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingMainBinding
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.view.productdetail.ProductDetailActivity
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ShoppingMainActivity : AppCompatActivity(), ShoppingMainContract.View {
    override lateinit var presenter: ShoppingMainContract.Presenter
    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private lateinit var recentProductWrapperAdapter: RecentProductWrapperAdapter

    private lateinit var binding: ActivityShoppingMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_main)

        setSupportActionBar(binding.tbProductCatalogue)
        setPresenter()
        setAdapters()
        setViewSettings()
        setButtonOnClick()
        setScrollView()
    }

    override fun onResume() {
        super.onResume()

        val recentProducts = presenter.getRecentProducts()
        recentProductAdapter.update(recentProducts)
        recentProductWrapperAdapter.update(recentProductAdapter.itemCount)
    }

    private fun setPresenter() {
        presenter = ShoppingMainPresenter(
            view = this,
            productsRepository = ProductMockRepository,
            recentProductsRepository = RecentProductRepositoryImpl(RecentProductDao(this))
        )
    }

    private fun setAdapters() {
        productAdapter = ProductAdapter(
            presenter.loadProducts(),
            showProductDetailPage()
        )
        recentProductAdapter = RecentProductAdapter(
            presenter.getRecentProducts(),
            showProductDetailPage()
        )
        recentProductWrapperAdapter = RecentProductWrapperAdapter(
            recentProductAdapter
        )

        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        concatAdapter = ConcatAdapter(config, recentProductWrapperAdapter, productAdapter)
    }

    private fun setButtonOnClick() {
        binding.btnLoadMore.setOnClickListener {
            presenter.loadMoreScroll()
        }
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

    private fun setScrollView() {
        binding.rvProductCatalogue.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    activateButton()
                } else {
                    deactivateButton()
                }
            }
        })
    }

    override fun showMoreProducts() {
        val updatedProducts = presenter.loadProducts()
        productAdapter.update(updatedProducts)
    }

    override fun deactivateButton() {
        binding.btnLoadMore.visibility = GONE
    }

    override fun activateButton() {
        if (!presenter.isPossibleLoad) {
            return
        }
        binding.btnLoadMore.visibility = VISIBLE
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
