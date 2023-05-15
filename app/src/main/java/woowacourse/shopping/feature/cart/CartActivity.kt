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
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartProductAdapter: CartProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        cartProductAdapter = CartProductAdapter()
        binding.cartItemRecyclerview.adapter = cartProductAdapter
        presenter = CartPresenter(this, CartRepositoryImpl(CartDao(this)))
        presenter.loadInitCartProduct()
        binding.presenter = presenter

        supportActionBar?.title = getString(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun changeCartProducts(newItems: List<CartProductUiModel>) {
        val newItemModels =
            newItems.map { it.toItemModel { cartId -> presenter.deleteCartProduct(cartId) } }
        cartProductAdapter.setItems(newItemModels)
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

    override fun exitCartScreen() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter.exit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_PAGE_KEY, presenter.page.currentPage)
        outState.putInt(ALL_SIZE_KEY, presenter.page.allSize)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY)
        val allSize = savedInstanceState.getInt(ALL_SIZE_KEY)
        presenter.setPage(PageUiModel(allSize, currentPage))
    }

    companion object {
        private const val CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY"
        private const val ALL_SIZE_KEY = "ALL_SIZE_KEY"

        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
