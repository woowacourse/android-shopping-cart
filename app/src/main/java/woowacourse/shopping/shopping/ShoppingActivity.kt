package woowacourse.shopping.shopping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.data.datasource.local.CartLocalDao
import woowacourse.shopping.data.datasource.local.RecentProductLocalDao
import woowacourse.shopping.data.datasource.local.ShopLocalDao
import woowacourse.shopping.data.datasource.remote.ProductRemoteDao
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.data.repository.ShopRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shopping.recyclerview.ProductAdapter
import woowacourse.shopping.shopping.recyclerview.RecentProductAdapter
import woowacourse.shopping.shopping.recyclerview.RecentProductWrapperAdapter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(
            emptyList(),
            onProductItemClick = { presenter.showProductDetail(it) },
            onMinusClick = { presenter.minusCartProduct(it) },
            onPlusClick = { presenter.plusCartProduct(it) },
            onCartAddClick = { presenter.plusCartProduct(it) }
        )
    }

    private val recentProductAdapter: RecentProductAdapter by lazy {
        RecentProductAdapter(emptyList())
    }

    private val recentProductWrapperAdapter: RecentProductWrapperAdapter by lazy {
        RecentProductWrapperAdapter(recentProductAdapter)
    }

    private val concatAdapter: ConcatAdapter by lazy {
        ConcatAdapter(
            recentProductWrapperAdapter, productAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        initProductList()
        initLoadMoreButton()
        initPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter.reloadProducts()
    }

    override fun updateProducts(cartProducts: List<CartProductModel>) {
        productAdapter.updateProducts(cartProducts)
    }

    override fun addProducts(cartProducts: List<CartProductModel>) {
        productAdapter.addProducts(cartProducts)
    }

    override fun updateRecentProducts(recentProducts: List<RecentProductModel>) {
        recentProductAdapter.updateRecentProducts(recentProducts)
        recentProductWrapperAdapter.updateRecentProduct()
    }

    override fun showProductDetail(cartProduct: CartProductModel, recentProduct: ProductModel?) {
        val intent = ProductDetailActivity.createIntent(this, cartProduct.product, recentProduct)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    override fun showCart() {
        startCartActivity()
    }

    override fun updateCartProductsCount(countOfProduct: Int) {
        binding.shoppingToolbarCartCountLayout.visibility = if (countOfProduct == 0) View.GONE
        else View.VISIBLE
        binding.shoppingToolbarCartCountText.text = countOfProduct.toString()
    }

    private fun initBinding() {
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.shoppingToolbar)
        binding.shoppingToolbarCartButton.setOnClickListener {
            presenter.openCart()
        }
    }

    private fun initLoadMoreButton() {
        binding.loadMoreButton.setOnClickListener {
            presenter.loadMoreProduct()
        }
    }

    private fun initProductList() {
        binding.shoppingProductList.layoutManager = makeLayoutManager()
        binding.shoppingProductList.itemAnimator = null
        binding.shoppingProductList.adapter = concatAdapter
    }

    private fun initPresenter() {
        presenter = ShoppingPresenter(
            this,
            productRepository = ProductRepository(ProductRemoteDao()),
            shopRepository = ShopRepository(ShopLocalDao(this)),
            cartRepository = CartRepository(CartLocalDao(this)),
            recentProductRepository = RecentProductRepository(RecentProductLocalDao(this)),
            recentProductSize = 10,
            productLoadSize = 20
        )
    }

    private fun startCartActivity() {
        val intent = CartActivity.createIntent(this)
        startActivity(intent)
    }

    private fun makeLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (concatAdapter.getWrappedAdapterAndPosition(position).first) {
                        is ProductAdapter -> 1
                        else -> 2
                    }
                }
            }
        }
    }
}
