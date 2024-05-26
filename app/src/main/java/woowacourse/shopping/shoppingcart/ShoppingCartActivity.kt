package woowacourse.shopping.shoppingcart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.productlist.ChangedItemsId
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.util.ViewModelFactory

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartClickAction {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter

    private val viewModel: ShoppingCartViewModel by viewModels { ViewModelFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupToolbar()
        initShoppingCart()

        adapter = ShoppingCartAdapter(this, viewModel)
        binding.rcvShoppingCart.adapter = adapter

        updateView()
        setUpdatedDataOnResult()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarShoppingCart as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.action_bar_title_shopping_cart_activity)
    }

    private fun initShoppingCart() {
        viewModel.loadCartItems(DEFAULT_CURRENT_PAGE)
        viewModel.updatePageSize()
    }

    private fun updateView() {
        viewModel.cartItemUiModels.observe(this) { cartItems ->
            adapter.submitList(cartItems)
        }

        viewModel.currentPage.observe(this) { currentPage ->
            viewModel.loadCartItems(currentPage)
        }
    }

    private fun setUpdatedDataOnResult() {
        viewModel.changedItems.observe(this) { changedItems ->
            val intent = Intent(this, ProductListActivity::class.java)
            intent.putExtra(ChangedItemsId.KEY_CHANGED_ITEMS, ChangedItemsId(changedItems))
            setResult(Activity.RESULT_OK, intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemRemoveBtnClicked(id: Long) {
        viewModel.deleteCartItem(id)
        viewModel.updatePageSize()
    }

    companion object {
        private const val DEFAULT_CURRENT_PAGE = 1

        fun newInstance(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
