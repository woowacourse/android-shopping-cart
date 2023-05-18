package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.utils.getSerializable
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.datasource.dao.CartDao
import woowacourse.shopping.data.datasource.dao.RecentProductDao
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogAddCartBinding

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private var addCartDialog: AlertDialog? = null
    private val shoppingDBOpenHelper: ShoppingDBOpenHelper by lazy {
        ShoppingDBOpenHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateExtras()
        initBinding()
        initToolbar()
        initProductDetailCartButton()
        initPresenter()
    }

    override fun initRecentProduct(recentProduct: ProductModel?) {
        if (recentProduct == null) return
        binding.recentProduct = recentProduct
        binding.onRecentProductClick = ::showProductDetail
    }

    override fun updateProductDetail(product: ProductModel) {
        binding.product = product
    }

    override fun openCartCounter(cartProduct: CartProductModel) {
        val addCartDialogBinding = DialogAddCartBinding.inflate(layoutInflater)
        val onMinusClick: (CartProductModel) -> Unit = {
            addCartDialogBinding.cartProduct = it.copy(amount = it.amount - 1)
        }
        val onPlusClick: (CartProductModel) -> Unit = {
            addCartDialogBinding.cartProduct = it.copy(amount = it.amount + 1)
        }
        val onClickAddCart: (CartProductModel) -> Unit = {
            presenter.addToCart(it)
        }

        addCartDialogBinding.cartProduct = cartProduct
        addCartDialogBinding.onMinusClick = onMinusClick
        addCartDialogBinding.onPlusClick = onPlusClick
        addCartDialogBinding.onClickAddCart = onClickAddCart

        addCartDialog = AlertDialog.Builder(this).apply {
            setView(addCartDialogBinding.root)
            create()
        }.show()
    }

    override fun close() {
        finish()
    }

    override fun showProductDetail(product: ProductModel) {
        val intent = createIntent(this, product, null)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_detail_close_action -> close()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        val dialog = addCartDialog ?: return
        if (dialog.isShowing) dialog.dismiss()
    }

    private fun validateExtras() {
        val bundle = intent.extras ?: return close()
        if (!bundle.containsKey(PRODUCT_EXTRA_NAME)) return close()
    }

    private fun initBinding() {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.productDetailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initPresenter() {
        val product = intent.getSerializable<ProductModel>(PRODUCT_EXTRA_NAME) ?: return
        val recentProduct = intent.getSerializable<ProductModel>(RECENT_PRODUCT_EXTRA_NAME)
        presenter = ProductDetailPresenter(
            this,
            product = product,
            recentProduct = recentProduct,
            recentProductRepository = RecentProductRepository(RecentProductDao(shoppingDBOpenHelper.writableDatabase)),
            cartRepository = CartRepository(CartDao(shoppingDBOpenHelper.writableDatabase))
        )
    }

    private fun initProductDetailCartButton() {
        binding.productDetailCartButton.setOnClickListener {
            presenter.showCartCounter()
        }
    }

    companion object {
        private const val PRODUCT_EXTRA_NAME = "product"
        private const val RECENT_PRODUCT_EXTRA_NAME = "recentProduct"

        fun createIntent(
            context: Context,
            product: ProductModel,
            recentProduct: ProductModel?
        ): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT_EXTRA_NAME, product)
            intent.putExtra(RECENT_PRODUCT_EXTRA_NAME, recentProduct)
            return intent
        }
    }
}
