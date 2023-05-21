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
        binding.lifecycleOwner = this

        initAdapter()
        initPresenter()

        supportActionBar?.title = getString(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPresenter() {
        val cartPresenter = CartPresenter(this, CartRepositoryImpl(CartDao(this)))
        presenter = cartPresenter
        binding.presenter = cartPresenter
        presenter.setup()
    }

    private fun initAdapter() {
        cartProductAdapter = CartProductAdapter(
            listOf(),
            object : CartProductClickListener {
                override fun onPlusClick(cartProduct: CartProductUiModel, previousCount: Int) {
                    presenter.increaseCartProduct(cartProduct.productUiModel, previousCount)
                }

                override fun onMinusClick(cartProduct: CartProductUiModel, previousCount: Int) {
                    presenter.decreaseCartProduct(cartProduct.productUiModel, previousCount)
                }

                override fun onCheckClick(cartProduct: CartProductUiModel, isSelected: Boolean) {
                    presenter.toggleCartProduct(cartProduct, isSelected)
                }

                override fun onDeleteClick(cartProduct: CartProductUiModel) {
                    presenter.deleteCartProduct(cartProduct.productUiModel)
                }
            }
        )
        binding.cartItemRecyclerview.adapter = cartProductAdapter
    }

    override fun changeCartProducts(newItems: List<CartProductUiModel>) {
        cartProductAdapter.setItems(newItems)
    }

    override fun setPageState(hasPrevious: Boolean, hasNext: Boolean, pageNumber: Int) {
        binding.apply {
            previousPageBtn.isEnabled = hasPrevious
            nextPageBtn.isEnabled = hasNext
            pageCountTextView.text = pageNumber.toString()
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
