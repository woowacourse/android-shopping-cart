package woowacourse.shopping.productlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.IntentCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActionLayoutCartIconBinding
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.ViewModelFactory

class ProductListActivity : AppCompatActivity(), ProductListClickAction {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter
    private val viewModel: ProductListViewModel by viewModels { ViewModelFactory(applicationContext) }
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        attachAdapter()
        showProducts()

        setLoadMoreButtonAction()
        setupToolBar()
        signUpActivityResults()
    }

    private fun attachAdapter() {
        adapter = ProductListAdapter(this)
        binding.rcvProductList.adapter = adapter
    }

    private fun showProducts() {
        viewModel.productUiModels.observe(this) { products ->
            adapter.submitList(products)
        }
        viewModel.updatedItemsId.observe(this) { updatedItemsId ->
            adapter.updateItems(updatedItemsId)
        }
    }

    private fun setLoadMoreButtonAction() {
        binding.btnLoadMoreProducts.setOnClickListener {
            viewModel.loadProducts(adapter.itemCount)
        }
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.toolbarProductList as Toolbar)
        supportActionBar?.title = getString(R.string.action_bar_title_product_list_activity)
    }

    private fun signUpActivityResults() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val changedItems =
                    result.data?.let {
                        IntentCompat.getParcelableExtra(
                            it,
                            ChangedItemsId.KEY_CHANGED_ITEMS,
                            ChangedItemsId::class.java,
                        )
                    } ?: ChangedItemsId(setOf())
                updateResultData(changedItems.ids)
            }
        }
    }

    private fun updateResultData(changedItems: Set<Long>) {
        viewModel.acceptChangedItems(changedItems)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        val menuBinding: ActionLayoutCartIconBinding =
            ActionLayoutCartIconBinding.inflate(layoutInflater)
        val menuItem = menu?.findItem(R.id.menu_shopping_cart_nav)
        menuItem?.actionView = menuBinding.root
        menuBinding.root.setOnClickListener {
            if (menuItem != null) {
                onOptionsItemSelected(menuItem)
            }
        }

        viewModel.totalItemQuantity.observe(this) {
            menuBinding.cartTotalItemQuantity = it
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_shopping_cart_nav -> {
                val intent = ShoppingCartActivity.newInstance(this)
                activityResultLauncher.launch(intent)
            }

            else -> {}
        }
        return true
    }

    override fun onProductClicked(id: Long) {
        val intent = ProductDetailActivity.newInstance(this, id)
        activityResultLauncher.launch(intent)
    }

    override fun onAddButtonClicked(id: Long) {
        viewModel.addProductToCart(id)
    }

    override fun onPlusButtonClicked(id: Long, currentQuantity: Int) {
        viewModel.plusProductOnCart(id, currentQuantity)
    }

    override fun onMinusButtonClicked(id: Long, currentQuantity: Int) {
        viewModel.minusProductOnCart(id, currentQuantity)
    }
}
