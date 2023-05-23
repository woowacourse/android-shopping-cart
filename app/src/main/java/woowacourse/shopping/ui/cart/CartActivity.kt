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
import woowacourse.shopping.datasource.cart.CartItemLocalDao
import woowacourse.shopping.datasource.product.ProductMemoryDao
import woowacourse.shopping.ui.cart.adapter.CartListAdapter
import woowacourse.shopping.ui.cart.presenter.CartPresenter
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class CartActivity : AppCompatActivity(), CartContract.View {
    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }
    private val presenter: CartPresenter by lazy {
        CartPresenter(
            this,
            CartItemRepositoryImpl(
                CartItemLocalDao(
                    DbHelper.getDbInstance(this),
                    ProductRepositoryImpl(ProductMemoryDao)
                )
            )
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
            presenter.onLoadCartItemsOfLastPage()
        }
    }

    private fun restoreStateIfSavedInstanceStateIsNotNull(savedInstanceState: Bundle?) {
        fun restoreSelectedCartItems(selectedCartItemIds: String) {
            presenter.restoreSelectedCartItems(selectedCartItemIds.split(" ").map { it.toLong() })
        }

        if (savedInstanceState != null) {
            presenter.restoreCurrentPage(savedInstanceState.getInt(CURRENT_PAGE))
            val selectedCartItemIds = savedInstanceState.getString(SELECTED_CART_ITEMS)
            if (!selectedCartItemIds.isNullOrBlank()) restoreSelectedCartItems(selectedCartItemIds)
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
            presenter.onLoadCartItemsOfPreviousPage()
        }
        binding.btnPageUp.setOnClickListener {
            presenter.onLoadCartItemsOfNextPage()
        }
    }

    private fun initCartList() {
        binding.recyclerViewCart.adapter = CartListAdapter(
            onClickCloseButton = { presenter.onDeleteCartItem(it) },
            onClickCheckBox = { id, isSelected ->
                presenter.onChangeSelectionOfCartItem(id, isSelected)
            },
            onClickPlus = { presenter.onPlusCount(it) },
            onClickMinus = { presenter.onMinusCount(it) }
        )
        presenter.onLoadCartItemsOfNextPage()
    }

    private fun initOrderUI() {
        binding.cbPageAllSelect.isChecked = false
        binding.cbPageAllSelect.setOnCheckedChangeListener { _, isChecked ->
            presenter.onChangeSelectionOfAllCartItems(isChecked)
        }
        binding.tvOrderPrice.text = getString(R.string.product_price).format(PRICE_FORMAT.format(0))
        binding.tvOrder.text = getString(R.string.order_with_count).format(0)
    }

    override fun setCartItems(cartItems: List<CartItemUIState>, initScroll: Boolean) {
        if (initScroll) {
            binding.recyclerViewCart.adapter = CartListAdapter(
                onClickCloseButton = { presenter.onDeleteCartItem(it) },
                onClickCheckBox = { productId, isSelected ->
                    presenter.onChangeSelectionOfCartItem(productId, isSelected)
                },
                onClickPlus = { presenter.onPlusCount(it) },
                onClickMinus = { presenter.onMinusCount(it) },
                cartItems = cartItems.toMutableList()
            )
            return
        }
        (binding.recyclerViewCart.adapter as CartListAdapter).setCartItems(cartItems)
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
        outState.putInt(CURRENT_PAGE, presenter.currentPage)
        outState.putString(SELECTED_CART_ITEMS, presenter.selectedCartItemIds.joinToString(" "))
        super.onSaveInstanceState(outState)
    }

    override fun setStateOfAllSelection(isAllSelected: Boolean) {
        binding.cbPageAllSelect.isChecked = isAllSelected
    }

    override fun setOrderPrice(price: Int) {
        binding.tvOrderPrice.text =
            getString(R.string.product_price).format(PRICE_FORMAT.format(price))
    }

    override fun setOrderCount(count: Int) {
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
