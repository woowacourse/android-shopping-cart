package woowacourse.shopping.ui.shopping

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.RecentProductUIModel
import woowacourse.shopping.repositoryImpl.MockWeb
import woowacourse.shopping.repositoryImpl.RemoteProductRepository
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detailedProduct.DetailedProductActivity
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapter
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapterDecoration.getItemDecoration
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapterDecoration.getSpanSizeLookup
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    private val adapter: ProductsAdapter = ProductsAdapter(getAdapterListener())
    private var tvCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initToolbar()
        initPresenter()
        initLayoutManager()
        presenter.setUpProducts()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateProducts()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initPresenter() {
        var url: String? = null
        val thread = Thread { url = MockWeb().url }
        thread.start()
        thread.join()
        presenter = ShoppingPresenter(
            this,
            RemoteProductRepository(url ?: ""),
            RecentProductDatabase(this),
            CartDatabase(this)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        menu?.findItem(R.id.cart)?.actionView?.let { view ->
            view.setOnClickListener { navigateToCart() }
            view.findViewById<TextView>(R.id.tv_counter)?.let { tvCount = it }
        }
        presenter.updateToolbar()
        return true
    }

    private fun initLayoutManager() {
        val layoutManager = binding.rvProducts.layoutManager as GridLayoutManager
        layoutManager.spanSizeLookup = getSpanSizeLookup(layoutManager, adapter)
        binding.rvProducts.addItemDecoration(getItemDecoration(layoutManager, resources))
    }

    private fun getAdapterListener() = object : ProductsListener {
        override fun onClickItem(productId: Int) {
            presenter.navigateToItemDetail(productId)
        }
        override fun onReadMoreClick() {
            presenter.addMoreProducts()
        }
        override fun onAddCartOrUpdateCount(productId: Int, count: Int) {
            adapter.let { adapter.updateItemCount(productId, count) }
            presenter.updateItem(productId, count)
        }
    }

    override fun setProducts(
        products: List<ProductUIModel>,
        recentProducts: List<RecentProductUIModel>,
        cartProducts: List<CartProductUIModel>
    ) {
        adapter.submitList(products, recentProducts, cartProducts)
        binding.rvProducts.adapter = adapter
        binding.rvProducts.itemAnimator = null
    }

    override fun addMoreProducts(products: List<ProductUIModel>) {
        adapter.addList(products)
    }

    override fun refreshProducts(
        recentProducts: List<RecentProductUIModel>,
        cartProducts: List<CartProductUIModel>
    ) {
        adapter.updateList(recentProducts, cartProducts)
    }

    override fun updateToolbar(count: Int) {
        tvCount?.text = count.toString()
    }

    override fun navigateToProductDetail(product: ProductUIModel) {
        startActivity(DetailedProductActivity.getIntent(this, product))
    }

    private fun navigateToCart() {
        startActivity(CartActivity.getIntent(this))
    }
}
