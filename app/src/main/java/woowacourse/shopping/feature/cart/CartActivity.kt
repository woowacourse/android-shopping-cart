package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.sql.cart.CartDao
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartProductAdapter: CartProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        cartProductAdapter = CartProductAdapter(listOf())
        binding.cartItemRecyclerview.adapter = cartProductAdapter
        presenter = CartPresenter(this, CartRepositoryImpl(CartDao(this)))
        presenter.loadInitCartProduct()

        supportActionBar?.title = getString(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initClickListener()
    }

    private fun initClickListener() {
        binding.previousPageBtn.setOnClickListener {
            presenter.loadPreviousPage(cartProductAdapter.items.first().cartProduct.cartId)
        }
        binding.nextPageBtn.setOnClickListener {
            presenter.loadNextPage(cartProductAdapter.items.last().cartProduct.cartId)
        }
    }

    override fun changeCartProducts(newItems: List<CartProductItemModel>) {
        cartProductAdapter.setItems(newItems)
    }

    override fun deleteCartProductFromScreen(position: Int) {
        presenter.deleteCartProduct(
            cartProductAdapter.items[position].cartProduct,
            cartProductAdapter.items.first().cartProduct.cartId
        )
    }

    override fun setPreviousButtonState(enabled: Boolean) {
        binding.previousPageBtn.isEnabled = enabled
    }

    override fun setNextButtonState(enabled: Boolean) {
        binding.nextPageBtn.isEnabled = enabled
    }

    override fun setCount(count: Int) {
        binding.pageCountTextView.text = count.toString()
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

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
