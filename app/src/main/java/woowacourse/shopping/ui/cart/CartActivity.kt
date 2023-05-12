package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.cartAdapter.CartAdapter
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initToolbar()
        initPresenter(savedInstanceState)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
    }

    private fun initPresenter(savedInstanceState: Bundle?) {
        presenter = CartPresenter(
            this,
            CartDatabase(CartDBHelper(this).writableDatabase),
            savedInstanceState?.getInt(KEY_OFFSET) ?: 0
        )
        presenter.setUpCarts()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setCarts(products: List<CartItemType.Cart>, cartUIModel: CartUIModel) {
        val cartListener = object : CartListener {
            override val onItemClick: (ProductUIModel) -> Unit
                get() = presenter::navigateToItemDetail
            override val onItemRemove: (Int) -> Unit
                get() = presenter::removeItem
            override val onPageUp: () -> Unit
                get() = presenter::pageUp
            override val onPageDown: () -> Unit
                get() = presenter::pageDown
        }

        binding.cartRecyclerview.adapter = CartAdapter(
            products.map { it }.plus(CartItemType.Navigation(cartUIModel)),
            cartListener
        )
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        startActivity(ProductDetailActivity.getIntent(this, product))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return false
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.getOffset().let {
            outState.putInt(KEY_OFFSET, it)
        }
    }
    companion object {
        private const val KEY_OFFSET = "KEY_OFFSET"
        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
