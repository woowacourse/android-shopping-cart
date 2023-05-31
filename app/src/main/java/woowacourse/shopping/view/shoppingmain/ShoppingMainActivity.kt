package woowacourse.shopping.view.shoppingmain

import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.CartProductDao
import woowacourse.shopping.data.db.MockProductService
import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
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
    private var cartBadge: TextView? = null

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
        presenter.updateCartBadge()
    }

    private fun setPresenter() {
        presenter = ShoppingMainPresenter(
            view = this,
            productsRepository = ProductRepositoryImpl(MockProductService()),
            cartProductRepository = CartProductRepositoryImpl(CartProductDao(this)),
            recentProductsRepository = RecentProductRepositoryImpl(RecentProductDao(this))
        )
    }

    private fun setAdapters() {
        val shoppingMainClickListener = object : ShoppingMainClickListener {
            override fun productOnClick(productUIModel: ProductUIModel) {
                showProductDetailPage()(productUIModel)
            }

            override fun findCartCountById(productUIModel: ProductUIModel): Int {
                return presenter.updateProductCartCount(productUIModel)
            }

            override fun addToCartOnClick(productUIModel: ProductUIModel) {
                presenter.addToCart(productUIModel)
            }

            override fun saveCartProductCount(productUIModel: ProductUIModel, count: Int) {
                presenter.updateCart(productUIModel, count)
            }
        }

        productAdapter = ProductAdapter(
            emptyList(),
            shoppingMainClickListener
        )
        presenter.loadProducts()
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
        intent.putExtra(BundleKeys.KEY_PRODUCT, it.id)
        intent.putExtra(BundleKeys.KEY_DEPTH, DEPTH_PARENT)
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

    override fun showMoreProducts(products: List<ProductUIModel>) {
        runOnUiThread {
            productAdapter.update(products)
        }
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

    override fun updateCartBadgeCount(count: Int) {
        cartBadge?.text = count.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_catalogue, menu)
        menu.findItem(R.id.menu_cart).actionView?.findViewById<ImageView>(R.id.iv_shopping_cart)
            ?.setOnClickListener {
                startActivity(ShoppingCartActivity.intent(this))
            }
        cartBadge = menu.findItem(R.id.menu_cart).actionView?.findViewById(R.id.tv_badge_cart)
        presenter.updateCartBadge()

        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val DEPTH_PARENT = 0
    }
}
