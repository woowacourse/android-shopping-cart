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
import woowacourse.shopping.database.cart.CartDao
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartNavigationUIModel
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import java.text.DecimalFormat

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        presenter = CartPresenter(
            this,
            CartRepositoryImpl(CartDao(this)),
            savedInstanceState?.getInt(KEY_OFFSET) ?: 0,
        )
        presenter.setUpCarts()
        binding.checkbox.setOnCheckedChangeListener { _, checked ->
            presenter.updateTotalChecked(checked)
        }
        setToolbar()
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

    override fun setCarts(products: List<CartItem>, cartNavigationUIModel: CartNavigationUIModel) {
        val cartAdapter = CartAdapter(
            products.map { it },
            presenter::navigateToItemDetail,
            presenter::removeItem,
            presenter::increaseCount,
            presenter::decreaseCount,
            presenter::updateChecked,
        )
        binding.cartRecyclerview.adapter = ConcatAdapter(
            cartAdapter,
            CartNavigationAdapter(cartNavigationUIModel, presenter::pageUp, presenter::pageDown),
        )
        binding.tvPrice.text = getString(R.string.product_price, "0")
        binding.tvOrder.text = getString(R.string.zero_order_text)
    }

    override fun navigateToItemDetail(cartProduct: CartProductUIModel) {
        startActivity(ProductDetailActivity.from(this, cartProduct.product))
    }

    override fun updatePrice(price: Int) {
        binding.tvPrice.text =
            getString(R.string.product_price, DecimalFormat("#,###").format(price))
    }

    override fun updateOrderCount(count: Int) {
        if (count == 0) {
            binding.tvOrder.text = getString(R.string.zero_order_text)
        } else {
            binding.tvOrder.text = getString(R.string.order_text, count)
        }
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
