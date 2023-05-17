package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import woowacourse.shopping.database.DbHelper
import woowacourse.shopping.database.cart.CartItemRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartListAdapter
import woowacourse.shopping.ui.cart.uistate.CartItemUIState

class CartActivity : AppCompatActivity(), CartContract.View {
    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }
    private val presenter: CartPresenter by lazy {
        CartPresenter(this, CartItemRepositoryImpl(DbHelper.getDbInstance(this), ProductRepositoryImpl))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()

        initCartList()
        initPageUI()
        loadLastPageIfFromCartItemAdd()
        restoreCurrentPageIfSavedInstanceStateIsNotNull(savedInstanceState)
    }

    private fun loadLastPageIfFromCartItemAdd() {
        if (intent.getBooleanExtra(JUST_ADDED_CART_ITEM, false)) {
            presenter.onLoadCartItemsLastPage()
        }
    }

    private fun restoreCurrentPageIfSavedInstanceStateIsNotNull(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter.restoreCurrentPage(savedInstanceState.getInt(CURRENT_PAGE))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navigationIcon = binding.toolbarCart.navigationIcon?.mutate()
        DrawableCompat.setTint(
            navigationIcon!!,
            ContextCompat.getColor(this, android.R.color.white),
        )
        binding.toolbarCart.navigationIcon = navigationIcon
    }

    private fun initCartList() {
        presenter.onLoadCartItemsNextPage()
    }

    private fun initPageUI() {
        binding.btnPageDown.setOnClickListener {
            presenter.onLoadCartItemsPreviousPage()
        }
        binding.btnPageUp.setOnClickListener {
            presenter.onLoadCartItemsNextPage()
        }
    }

    override fun setCartItems(cartItems: List<CartItemUIState>) {
        binding.recyclerViewCart.adapter = CartListAdapter(cartItems) {
            presenter.onDeleteCartItem(it)
        }
    }

    override fun setStateThatCanRequestNextPage(canRequest: Boolean) {
        binding.btnPageUp.isEnabled = canRequest
    }

    override fun setStateThatCanRequestPreviousPage(canRequest: Boolean) {
        binding.btnPageDown.isEnabled = canRequest
    }

    override fun setPage(page: Int) {
        binding.tvCartPage.text = page.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_PAGE, presenter.getCurrentPage())
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val CURRENT_PAGE = "CURRENT_PAGE"
        private const val JUST_ADDED_CART_ITEM = "JUST_ADDED_CART_ITEM"

        fun startActivity(context: Context, justAddedCartItem: Boolean = false) {
            Intent(context, CartActivity::class.java).apply {
                putExtra(JUST_ADDED_CART_ITEM, justAddedCartItem)
            }.run {
                context.startActivity(this)
            }
        }
    }
}
