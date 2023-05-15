package woowacourse.shopping.shopping

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.productdetail.ProductDetailActivity.Companion.ACTIVITY_RESULT_CODE
import woowacourse.shopping.shoppingcart.ShoppingCartActivity

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingRecyclerAdapter: ShoppingRecyclerAdapter
    private val presenter: ShoppingContract.Presenter by lazy {
        ShoppingPresenter.of(this, this)
    }
    private val getResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                ACTIVITY_RESULT_CODE -> {
                    presenter.updateRecentViewedProducts()
                }
            }
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
            R.id.shopping_cart -> {
                startActivity(Intent(this, ShoppingCartActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setUpShoppingView(
        products: List<ProductUiModel>,
        recentViewedProducts: List<ProductUiModel>,
        showMoreShoppingProducts: () -> (Unit),
    ) {
        shoppingRecyclerAdapter = ShoppingRecyclerAdapter(
            products = products,
            recentViewedProducts = recentViewedProducts,
            onProductClicked = ::navigateToProductDetailView,
        )

        with(binding) {
            productRecyclerView.layoutManager = GridLayoutManager(root.context, 2).apply {
                spanSizeLookup =
                    ShoppingRecyclerSpanSizeManager(shoppingRecyclerAdapter::getItemViewType)
            }
            productRecyclerView.adapter = shoppingRecyclerAdapter
            buttonShowMore.setOnClickListener {
                buttonShowMore.visibility = View.GONE
                showMoreShoppingProducts()
            }
            productRecyclerView.addOnScrollListener(
                ShoppingRecyclerScrollListener(
                    scrollPossible = { buttonShowMore.visibility = View.GONE },
                    scrollImpossible = { buttonShowMore.visibility = View.VISIBLE },
                ),
            )
        }
    }

    override fun refreshRecentViewedProductsView(toReplace: List<ProductUiModel>) {
        shoppingRecyclerAdapter.refreshRecentViewedItems(toReplace)
    }

    override fun refreshShoppingProductsView(toAdd: List<ProductUiModel>) {
        shoppingRecyclerAdapter.refreshShoppingItems(toAdd = toAdd)
    }

    private fun navigateToProductDetailView(product: ProductUiModel) {
        presenter.addToRecentViewedProduct(product.id)
        val intent = ProductDetailActivity.getIntent(this, product)

        getResult.launch(intent)
    }
}
