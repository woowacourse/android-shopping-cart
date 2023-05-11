package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartListAdapter
import woowacourse.shopping.ui.cart.uistate.CartUIState
import kotlin.properties.Delegates

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private val presenter: CartPresenter by lazy {
        CartPresenter(this, CartRepositoryImpl(this, ProductRepositoryImpl))
    }
    private var page: Int by Delegates.observable(1) { _, _, new ->
        presenter.loadCartItems(PAGE_SIZE, (new - 1) * PAGE_SIZE)
        binding.tvCartPage.text = "$page"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        initCartList()
        initBottomField()
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
        presenter.loadCartItems(limit = PAGE_SIZE, offset = (page - 1) * PAGE_SIZE)
    }

    private fun initBottomField() {
        binding.tvCartPage.text = "$page"
        presenter.setPageButtons(PAGE_SIZE)
    }

    override fun setButtonClickListener(maxOffset: Int) {
        updateButtonsEnabledState(maxOffset)

        binding.btnPageDown.setOnClickListener {
            if (page > 1) {
                page--
            }
            updateButtonsEnabledState(maxOffset)
        }
        binding.btnPageUp.setOnClickListener {
            if (page < maxOffset) {
                page++
            }
            updateButtonsEnabledState(maxOffset)
        }
    }

    override fun setCartItems(cartItems: List<CartUIState>) {
        binding.recyclerViewCart.adapter = CartListAdapter(cartItems) {
            presenter.deleteCartItem(cartItems[it].id)
        }
    }

    private fun updateButtonsEnabledState(maxOffset: Int) {
        binding.btnPageDown.isEnabled = page > 1
        binding.btnPageUp.isEnabled = page < maxOffset
    }

    companion object {
        private const val PAGE_SIZE = 5

        fun startActivity(context: Context) {
            Intent(context, CartActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}
