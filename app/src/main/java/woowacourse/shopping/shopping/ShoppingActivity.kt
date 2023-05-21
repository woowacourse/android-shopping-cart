package woowacourse.shopping.shopping

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingCacheImpl
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingRecyclerAdapter: ShoppingRecyclerAdapter
    private val presenter: ShoppingContract.Presenter by lazy {
        ShoppingPresenter(
            view = this,
            shoppingCache = ShoppingCacheImpl(
                shoppingDao = ShoppingDao(this)
            )
        )
    }
    override val shoppingNavigator: ShoppingNavigator by lazy {
        ShoppingNavigatorImpl(this)
    }

    override fun onResume() {
        super.onResume()

        presenter.loadCartProductsCount()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)

        setSupportActionBar(binding.toolbarShopping)
        presenter.loadProducts()
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
        products: List<ProductUiModel>,
        recentViewedProducts: List<RecentViewedProductUiModel>,
    ) {
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
        override fun onPlus(product: ProductUiModel) {
            presenter.plusShoppingCartProductCount(product)
        }

        override fun onMinus(product: ProductUiModel) {
            presenter.minusShoppingCartProductCount(product)
        }

        override fun onAdded(product: ProductUiModel) {
            presenter.addProductToShoppingCart(product)
        }
    }

    override fun refreshRecentViewedProductsView(products: List<RecentViewedProductUiModel>) {
        shoppingRecyclerAdapter.refreshRecentViewedItems(products = products)
    }

    override fun refreshShoppingProductsView(toAdd: List<ProductUiModel>) {
        if (toAdd.isEmpty()) {
            return showMessageNothingToAdd()
        }
        shoppingRecyclerAdapter.refreshShoppingItems(toAdd = toAdd)
    }

    private fun showMessageNothingToAdd() {
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
