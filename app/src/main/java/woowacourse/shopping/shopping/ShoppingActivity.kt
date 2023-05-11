package woowacourse.shopping.shopping

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.ShoppingCartActivity

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingRecyclerAdapter: ShoppingRecyclerAdapter
    private val presenter: ShoppingContract.Presenter by lazy {
        ShoppingPresenter(
            view = this,
            repository = ShoppingDBAdapter(
                shoppingDao = ShoppingDao(this)
            ),
        )
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
    ) {
        shoppingRecyclerAdapter = ShoppingRecyclerAdapter(
            products = products,
            recentViewedProducts = recentViewedProducts,
            onProductClicked = ::navigateToProductDetailView
        )
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup =
                ShoppingRecyclerSpanSizeManager(shoppingRecyclerAdapter::getItemViewType)
        }
        binding.productRecyclerView.adapter = shoppingRecyclerAdapter
        binding.button.setOnClickListener {
            binding.button.visibility = View.GONE
        }
        binding.productRecyclerView.addOnScrollListener(
            ShoppingRecyclerScrollListener(
                scrollPossible = { binding.button.visibility = View.GONE },
                scrollImpossible = { binding.button.visibility = View.VISIBLE }
            )
        )
    }

    override fun refreshShoppingView(
        toAdd: ProductUiModel,
        toRemove: ProductUiModel?
    ) {

        shoppingRecyclerAdapter.refresh(
            toRemove = toRemove,
            toAdd = toAdd
        )
    }

    private fun navigateToProductDetailView(product: ProductUiModel) {
        presenter.addToRecentViewedProduct(product.id)
        val intent = ProductDetailActivity.getIntent(this, product)
        startActivity(intent)
    }
}
