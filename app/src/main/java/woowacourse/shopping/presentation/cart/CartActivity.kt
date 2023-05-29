package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductServiceHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.model.CartProductInfoListModel
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.noIntentExceptionHandler

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartProductPriceView: TextView
    private lateinit var cartProductsModel: List<CartProductInfoModel>
    private val presenter: CartContract.Presenter by lazy { initPresenter() }

    private fun initProductModel() {
        intent.getParcelableExtraCompat<CartProductInfoListModel>(CART_PRODUCTS_KEY)
            ?.let { receivedCartProduct ->
                cartProductsModel = receivedCartProduct.items
            } ?: noIntentExceptionHandler(getString(R.string.product_model_null_error_message))
    }

    private fun initPresenter(): CartContract.Presenter {
        initProductModel()
        return CartPresenter(
            this,
            CartRepositoryImpl(CartDao(CartDbHelper(this)), ProductServiceHelper),
            cartProductsModel,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        initView()
        managePaging()
    }

    private fun setUpBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initView() {
        initCartAdapter()
        setToolBar()
        updateView()
        allOrderedCheckBoxChange()
    }

    private fun initCartAdapter() {
        cartAdapter = CartAdapter(
            presenter = presenter,
            updateProductPrice = ::updateProductPrice,
        )
        binding.recyclerCart.adapter = cartAdapter
    }

    private fun setToolBar() {
        setSupportActionBar(binding.toolbarCart.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back_24)
    }

    private fun updateView() {
        presenter.refreshCurrentPage()
        presenter.checkPlusPageAble()
        presenter.checkMinusPageAble()
        presenter.checkCurrentPageProductsOrderState()
        presenter.updateOrderPrice()
        presenter.updateOrderCount()
    }

    private fun managePaging() {
        onPlusPage()
        onMinusPage()
    }

    private fun onPlusPage() {
        binding.buttonPlusPage.setOnClickListener {
            presenter.plusPage()
            updateView()
        }
    }

    private fun onMinusPage() {
        binding.buttonMinusPage.setOnClickListener {
            presenter.minusPage()
            updateView()
        }
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

    private fun allOrderedCheckBoxChange() {
        binding.checkboxAllCart.setOnCheckedChangeListener { _, isChecked ->
            presenter.changeCurrentPageProductsOrder(isChecked)
            presenter.updateOrderPrice()
            presenter.updateOrderCount()
        }
    }

    override fun setCartItems(productModels: List<CartProductInfoModel>) {
        cartAdapter.submitList(productModels)
    }

    override fun setUpPlusPageState(isEnable: Boolean) {
        binding.buttonPlusPage.isClickable = isEnable
        if (isEnable) {
            binding.buttonPlusPage.setImageResource(R.drawable.icon_right_page_true)
        } else {
            binding.buttonPlusPage.setImageResource(R.drawable.icon_right_page_false)
        }
    }

    override fun setUpMinusPageState(isEnable: Boolean) {
        binding.buttonMinusPage.isClickable = isEnable
        if (isEnable) {
            binding.buttonMinusPage.setImageResource(R.drawable.icon_left_page_true)
        } else {
            binding.buttonMinusPage.setImageResource(R.drawable.icon_left_page_false)
        }
    }

    override fun setAllOrderState(isAllOrdered: Boolean) {
        binding.checkboxAllCart.isChecked = isAllOrdered
    }

    private fun updateProductPrice(textView: TextView, cartProductInfoModel: CartProductInfoModel) {
        cartProductPriceView = textView
        presenter.updateProductPrice(cartProductInfoModel)
    }

    override fun setProductPrice(price: Int) {
        cartProductPriceView.text = getString(R.string.price_format, price)
    }

    override fun setPage(page: String) {
        binding.textCartPage.text = page
    }

    override fun setOrderPrice(totalPrice: Int) {
        binding.textCartPrice.text = getString(R.string.price_format, totalPrice)
    }

    override fun setOrderCount(count: Int) {
        binding.buttonCartOrder.text = getString(R.string.order_format, count)
    }

    companion object {
        private const val CART_PRODUCTS_KEY = "CART_PRODUCTS_KEY"
        fun getIntent(context: Context, cartProducts: CartProductInfoListModel): Intent {
            val intent = Intent(context, CartActivity::class.java)
            intent.putExtra(CART_PRODUCTS_KEY, cartProducts)
            return intent
        }
    }
}
