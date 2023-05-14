package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbAdapter
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.product.MockProductRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private val presenter: CartContract.Presenter by lazy {
        CartPresenter(this, CartDbAdapter(CartDbHelper(this)), MockProductRepository)
    }
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        managePaging()
    }

    private fun initView() {
        presenter
        setToolBar()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.toolbarCart.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back_24)
    }

    private fun managePaging() {
        onPlusPage()
        onMinusPage()
    }

    private fun onPlusPage() {
        binding.buttonRightPage.setOnClickListener {
            presenter.plusPage()
            updateView()
        }
    }

    private fun onMinusPage() {
        binding.buttonLeftPage.setOnClickListener {
            presenter.minusPage()
            updateView()
        }
    }

    private fun updateView() {
        presenter.updateCart()
        presenter.updateLeftPageState()
        presenter.updateRightPageState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun initCartItems(cartProductModels: List<ProductModel>) {
        cartAdapter = CartAdapter(::deleteCartProduct)
        binding.recyclerCart.adapter = cartAdapter
    }

    private fun deleteCartProduct(productModel: ProductModel) {
        presenter.deleteProduct(productModel)
        updateView()
    }

    override fun setCartItems(productModels: List<ProductModel>) {
        cartAdapter.submitList(productModels)
    }

    override fun setPage(count: Int) {
        binding.textCartPage.text = count.toString()
    }

    override fun setRightPageState(isEnable: Boolean) {
        binding.buttonRightPage.isClickable = isEnable
        if (isEnable) {
            binding.buttonRightPage.setImageResource(R.drawable.icon_right_page_true)
        } else {
            binding.buttonRightPage.setImageResource(R.drawable.icon_right_page_false)
        }
    }

    override fun setLeftPageState(isEnable: Boolean) {
        binding.buttonLeftPage.isClickable = isEnable
        if (isEnable) {
            binding.buttonLeftPage.setImageResource(R.drawable.icon_left_page_true)
        } else {
            binding.buttonLeftPage.setImageResource(R.drawable.icon_left_page_false)
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
