package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import woowacourse.shopping.R
import woowacourse.shopping.cart.contract.CartContract
import woowacourse.shopping.cart.contract.presenter.CartPresenter
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.ProductDetailActivity

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return false
        }
        return true
    }

    override fun setCarts(products: List<CartItem>, cartUIModel: CartUIModel) {
        binding.cartRecyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this
        )

        val cartAdapter = CartAdapter(
            products.map { it },
            presenter::navigateToItemDetail,
            presenter::removeItem
        )
        binding.cartRecyclerview.adapter = ConcatAdapter(
            cartAdapter,
            CartNavigationAdapter(cartUIModel, presenter::pageUp, presenter::pageDown)
        )
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        startActivity(ProductDetailActivity.from(this, product))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.getOffset().let {
            outState.putInt(KEY_OFFSET, it)
        }
    }
    companion object {
        private const val KEY_OFFSET = "KEY_OFFSET"
        fun from(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
