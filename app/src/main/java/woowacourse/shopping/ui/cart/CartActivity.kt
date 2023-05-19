package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import woowacourse.shopping.R
import woowacourse.shopping.database.DbHelper
import woowacourse.shopping.database.cart.CartItemRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartListAdapter
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class CartActivity : AppCompatActivity() {
    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }
    private val presenter: CartPresenter by lazy {
        CartPresenter(
            this,
            CartItemRepositoryImpl(DbHelper.getDbInstance(this), ProductRepositoryImpl)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()

        initPageUI()
        initCartList()
        initOrderUI()
        loadLastPageIfFromCartItemAdd()
        restoreStateIfSavedInstanceStateIsNotNull(savedInstanceState)
    }

    private fun loadLastPageIfFromCartItemAdd() {
        if (intent.getBooleanExtra(JUST_ADDED_CART_ITEM, false)) {
            presenter.onLoadCartItemsLastPage()
        }
    }

    private fun restoreStateIfSavedInstanceStateIsNotNull(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter.restoreCurrentPage(savedInstanceState.getInt(CURRENT_PAGE))
            val selectedCartItems = savedInstanceState.getString(SELECTED_CART_ITEMS)
                ?.split(" ")
                ?.map { it.toLong() }
                ?.toSet()
            if (selectedCartItems != null) presenter.restoreSelectedCartItems(selectedCartItems)
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

    private fun initPageUI() {
        binding.btnPageDown.setOnClickListener {
            presenter.onLoadCartItemsPreviousPage()
        }
        binding.btnPageUp.setOnClickListener {
            presenter.onLoadCartItemsNextPage()
        }
    }

    private fun initCartList() {
        binding.recyclerViewCart.adapter = CartListAdapter(
            mutableListOf(),
            onCloseButtonClick = { presenter.onDeleteCartItem(it) },
            onCheckButtonClick = { productId, isSelected ->
                presenter.onChangeCartItemSelection(productId, isSelected)
            },
            onPlusCountClick = { presenter.onPlusCountOfProduct(it) },
            onMinusCountClick = { presenter.onMinusCountOfProduct(it) }
        )
        presenter.onLoadCartItemsNextPage()
    }

    private fun initOrderUI() {
        binding.cbPageAllSelect.isChecked = false
        binding.cbPageAllSelect.setOnCheckedChangeListener { _, isChecked ->
            presenter.onChangeCartItemsOnCurrentPageSelection(isChecked)
        }
        binding.tvOrderPrice.text = getString(R.string.product_price).format(PRICE_FORMAT.format(0))
        binding.tvOrder.text = getString(R.string.order_with_count).format(0)
    }

    fun setCartItems(cartItems: List<CartItemUIState>) {
        binding.recyclerViewCart.adapter = CartListAdapter(
            cartItems = cartItems.toMutableList(),
            onCloseButtonClick = { presenter.onDeleteCartItem(it) },
            onCheckButtonClick = { productId, isSelected ->
                presenter.onChangeCartItemSelection(productId, isSelected)
            },
            onPlusCountClick = { presenter.onPlusCountOfProduct(it) },
            onMinusCountClick = { presenter.onMinusCountOfProduct(it) }
        )
    }

    fun setStateThatCanRequestNextPage(canRequest: Boolean) {
        binding.btnPageUp.isEnabled = canRequest
    }

    fun setStateThatCanRequestPreviousPage(canRequest: Boolean) {
        binding.btnPageDown.isEnabled = canRequest
    }

    fun setPage(page: Int) {
        binding.tvCartPage.text = page.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_PAGE, presenter.currentPage)
        outState.putString(SELECTED_CART_ITEMS, presenter.selectedCartItems.joinToString(" "))
        super.onSaveInstanceState(outState)
    }

    fun setAllCartItemsSelected(isSelected: Boolean) {
        binding.cbPageAllSelect.isChecked = isSelected
    }

    fun setOrderPrice(price: Int) {
        binding.tvOrderPrice.text =
            getString(R.string.product_price).format(PRICE_FORMAT.format(price))
    }

    fun setOrderCount(count: Int) {
        binding.tvOrder.text = getString(R.string.order_with_count).format(count)
    }

    companion object {
        private const val CURRENT_PAGE = "CURRENT_PAGE"
        private const val SELECTED_CART_ITEMS = "SELECTED_CART_ITEMS"
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
