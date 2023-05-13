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
    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(listOf(), presenter::deleteProduct)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
        initCartAdapter()
    }

    private fun setView() {
        setToolBar()
        initLeftClick()
        initRightClick()
    }

    override fun setPage(count: Int) {
        binding.textCartPage.text = count.toString()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.toolbarCart.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back_24)
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

    private fun initCartAdapter() {
        binding.recyclerCart.adapter = cartAdapter
        presenter.initCart()
    }

    override fun setCartProductModels(productModels: List<ProductModel>) {
        cartAdapter.setItems(productModels)
    }

    private fun initRightClick() {
        binding.buttonRightPage.setOnClickListener {
            presenter.plusPage()
        }
    }

    private fun initLeftClick() {
        binding.buttonLeftPage.setOnClickListener {
            presenter.minusPage()
        }
    }

    override fun setRightPageEnable(isEnable: Boolean) {
        binding.buttonRightPage.isClickable = isEnable
        if (isEnable) {
            binding.buttonRightPage.setImageResource(R.drawable.icon_right_page_true)
        } else {
            binding.buttonRightPage.setImageResource(R.drawable.icon_right_page_false)
        }
    }

    override fun setLeftPageEnable(isEnable: Boolean) {
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
