package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.contract.CartContract
import woowacourse.shopping.ui.cart.contract.presenter.CartPresenter
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        presenter = CartPresenter(
            this,
            CartDatabase(CartDBHelper(this).writableDatabase),
            savedInstanceState?.getInt(KEY_OFFSET) ?: 0,
        )
        setToolbar()
        presenter.setUpCarts()

        binding.allCheckBox.setOnCheckedChangeListener { _, isChecked ->
            presenter.onAllCheckboxClick(isChecked)
        }
    }

    private fun setToolbar() {
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

    override fun setCarts(products: List<CartProductUIModel>, cartUIModel: CartUIModel) {
        binding.cartRecyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
        )

        val cartAdapter = CartAdapter(
            products.map { it },
            presenter::navigateToItemDetail,
            presenter::removeItem,
            presenter::onChangeCartCount,
            presenter::onCheckChanged,
        )

        binding.cartRecyclerview.adapter = ConcatAdapter(
            cartAdapter,
            CartNavigationAdapter(cartUIModel, presenter::pageUp, presenter::pageDown),
        )
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        startActivity(ProductDetailActivity.from(this, product, true))
    }

    override fun setCartItemsPrice(price: Int) {
        binding.executePendingBindings()
        binding.price = price
    }

    override fun updateCheckboxItem(id: Long, checked: Boolean) {
        binding.cartRecyclerview.adapter?.let {
            if (it is ConcatAdapter) {
                val cartAdapter = it.adapters.filterIsInstance<CartAdapter>().firstOrNull()
                cartAdapter?.updateItem(id, checked)
            }
        }
    }

    override fun setAllCheckbox(isChecked: Boolean) {
        binding.allCheckBox.isChecked = isChecked
    }

    override fun setAllOrderCount(count: Int) {
        binding.executePendingBindings()
        binding.count = count
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val state = mutableMapOf<String, Int>()
        presenter.saveOffsetState(state)
        state[KEY_OFFSET]?.let { outState.putInt(KEY_OFFSET, it) }
    }

    companion object {
        const val KEY_OFFSET = "KEY_OFFSET"
        fun from(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
