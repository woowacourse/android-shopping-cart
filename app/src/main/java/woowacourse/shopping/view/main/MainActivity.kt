package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.base.ActivityBoilerPlateCode
import woowacourse.shopping.view.base.ActivityBoilerPlateCodeImpl
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.main.adapter.ProductEventHandler
import woowacourse.shopping.view.main.adapter.ProductsAdapter
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.view.uimodel.ProductUiModel
import kotlin.getValue

class MainActivity :
    AppCompatActivity(),
    ActivityBoilerPlateCode<ActivityMainBinding> by ActivityBoilerPlateCodeImpl(
        R.layout.activity_main,
    ) {
    private val viewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(
            ProductEventHandlerImpl(viewModel.totalShoppingCartSize),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setSupportActionBar(binding.toolbar as Toolbar)
        initProductList()
    }

    override fun onResume() {
        super.onResume()
        updateProductList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_item_shopping_cart)?.actionView?.let { view ->
            view.setOnClickListener {
                onShoppingCartClicked()
            }
            view.findViewById<TextView>(R.id.shopping_cart_alarm_badge).apply {
                viewModel.totalShoppingCartSize.observe(this@MainActivity) { it ->
                    text = it.toString()
                    visibility = viewModel.menuBadgeViewRule(text.toString())
                }
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_shopping_cart) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onLoadMoreProducts(page: Int) {
        binding.btnLoadMoreProducts.visibility = View.GONE
        viewModel.requestProductsPage(page)
    }

    private fun onShoppingCartClicked() {
        viewModel.saveCurrentShoppingCart(productsAdapter.quantityInfo)
        val intent = ShoppingCartActivity.newIntent(this)
        startActivity(intent)
    }

    private fun initProductList() {
        binding.apply {
            viewModel = this@MainActivity.viewModel
            onLoadMoreProducts = ::onLoadMoreProducts
        }
        binding.productList.apply {
            adapter = productsAdapter
            addOnScrollListener(ProductsOnScrollListener(binding, viewModel))
        }
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@MainActivity) { mainRecyclerViewProduct ->
                productsAdapter.updateProducts(mainRecyclerViewProduct)
            }
        }
    }

    private fun updateProductList() {
        productsAdapter.clear()
        viewModel.updateShoppingCart(productsAdapter.currentPage)
    }

    private inner class ProductEventHandlerImpl(
        val totalShoppingCartSize: MutableLiveData<Int>,
    ) : ProductEventHandler {
        override fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>) {
            super.onQuantityPlusSelected(quantity)
            totalShoppingCartSize.value = totalShoppingCartSize.value?.inc()
        }

        override fun onQuantityMinusSelected(quantity: MutableLiveData<Int>) {
            super.onQuantityMinusSelected(quantity)
            totalShoppingCartSize.value = totalShoppingCartSize.value?.dec()
        }

        override fun onQuantityPlusSelected(quantity: MutableLiveData<Int>) {
            super.onQuantityPlusSelected(quantity)
            totalShoppingCartSize.value = totalShoppingCartSize.value?.inc()
        }

        override fun onProductSelected(productUiModel: ProductUiModel) {
            viewModel.saveCurrentShoppingCart(productsAdapter.quantityInfo)
            this@MainActivity.startActivity(ProductDetailActivity.newIntent(this@MainActivity, productUiModel))
        }

        override fun whenQuantityChangedSelectView(
            view: ViewGroup,
            quantity: MutableLiveData<Int>,
        ) {
            val btnItemProductAddToCart = view.findViewById<View>(R.id.btn_item_product_add_to_cart)
            val layoutProductQuantitySelector = view.findViewById<View>(R.id.layout_product_quantity_selector)

            quantity.value?.let { quantityValue ->
                btnItemProductAddToCart.visibility =
                    viewModel.onProductAddedButtonViewRule(quantityValue)
                layoutProductQuantitySelector.visibility =
                    viewModel.onProductAddedQuantitySelectorViewRule(quantityValue)
            }
        }
    }
}

private class ProductsOnScrollListener(
    private val binding: ActivityMainBinding,
    private val viewModel: ProductsViewModel,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        binding.btnLoadMoreProducts.visibility =
            if (
                lastVisibleItemPosition == layoutManager.itemCount - 1 &&
                viewModel.totalSize > (recyclerView.adapter?.itemCount ?: 0)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}
