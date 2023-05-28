package woowacourse.shopping.shopping

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDao
import woowacourse.shopping.data.cart.cache.CartCacheImpl
import woowacourse.shopping.data.cart.datasource.CartDataSourceImpl
import woowacourse.shopping.data.cart.repository.CartRepositoryImpl
import woowacourse.shopping.data.product.repository.ProductRepositoryImpl
import woowacourse.shopping.data.recentviewed.cache.RecentViewedProductCacheImpl
import woowacourse.shopping.data.recentviewed.datasource.RecentViewedProductDataSourceImpl
import woowacourse.shopping.data.recentviewed.repository.RecentViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel
import woowacourse.shopping.shopping.adapter.ProductItemDecoration
import woowacourse.shopping.shopping.adapter.ShoppingProductCountPicker
import woowacourse.shopping.shopping.adapter.ShoppingRecyclerAdapter
import woowacourse.shopping.shopping.adapter.ShoppingRecyclerSpanSizeManager
import woowacourse.shopping.shopping.navigator.ShoppingNavigator
import woowacourse.shopping.shopping.navigator.ShoppingNavigatorImpl

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingRecyclerAdapter: ShoppingRecyclerAdapter
    private val presenter: ShoppingContract.Presenter by lazy {
        ShoppingPresenter(
            view = this,
            productRepository = ProductRepositoryImpl(),
            cartRepository = CartRepositoryImpl(
                cartDataSource = CartDataSourceImpl(
                    cartCache = CartCacheImpl(
                        shoppingDao = ShoppingDao(this)
                    )
                )
            ),
            recentViewedProductRepository = RecentViewedProductRepositoryImpl(
                recentViewedProductDataSource = RecentViewedProductDataSourceImpl(
                    recentViewedProductCache = RecentViewedProductCacheImpl(
                        shoppingDao = ShoppingDao(this)
                    )
                )
            )
        )
    }
    override val shoppingNavigator: ShoppingNavigator by lazy {
        ShoppingNavigatorImpl(this)
    }

    override fun onRestart() {
        super.onRestart()

        presenter.loadCartProductsCount()
        presenter.loadProducts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)

        setSupportActionBar(binding.toolbarShopping)
        presenter.setUpProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shopping, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart -> shoppingNavigator.navigateToCartView()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setUpShoppingView(
        products: List<CartProductUiModel>,
        recentViewedProducts: List<RecentViewedProductUiModel>,
    ) {
        binding.productRecyclerView.itemAnimator = null
        shoppingRecyclerAdapter = ShoppingRecyclerAdapter(
            products = products,
            recentViewedProducts = recentViewedProducts,
            onProductClicked = presenter::loadProductDetail,
            onReadMoreButtonClicked = presenter::readMoreShoppingProducts,
            productCountPickerListener = getProductCountPickerListenerImpl()
        )

        with(binding) {
            productRecyclerView.layoutManager = GridLayoutManager(root.context, 2).apply {
                spanSizeLookup =
                    ShoppingRecyclerSpanSizeManager(shoppingRecyclerAdapter::getItemViewType)
            }
            productRecyclerView.adapter = shoppingRecyclerAdapter
            productRecyclerView.addItemDecoration(
                ProductItemDecoration(
                    getItemViewType = shoppingRecyclerAdapter::getItemViewType,
                )
            )
        }
    }

    private fun getProductCountPickerListenerImpl() = object : ShoppingProductCountPicker {
        override fun onPlus(product: CartProductUiModel) {
            presenter.plusShoppingCartProductCount(product)
        }

        override fun onMinus(product: CartProductUiModel) {
            presenter.minusShoppingCartProductCount(product)
        }

        override fun onAdded(product: CartProductUiModel) {
            presenter.addProductToShoppingCart(product)
        }
    }

    override fun refreshRecentViewedProductsView(products: List<RecentViewedProductUiModel>) {
        shoppingRecyclerAdapter.refreshRecentViewedItems(products = products)
    }

    override fun refreshShoppingProductsView(products: List<CartProductUiModel>) {
        shoppingRecyclerAdapter.refreshShoppingProductsItem(products)
    }

    override fun showMoreProducts(toAdd: List<CartProductUiModel>) {
        if (toAdd.isEmpty()) {
            return showMessageNothingToRead()
        }
        shoppingRecyclerAdapter.addShoppingItems(toAdd = toAdd)
    }

    private fun showMessageNothingToRead() {
        Toast.makeText(
            this,
            getString(R.string.message_last_product),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun refreshProductCount(count: Int) {
        binding.textShoppingCartProductsCount.text = count.toString()
    }
}
