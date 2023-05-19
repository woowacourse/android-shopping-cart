package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogCartBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.ProductUIModel.Companion.KEY_PRODUCT
import woowacourse.shopping.productdetail.contract.ProductDetailContract
import woowacourse.shopping.productdetail.contract.presenter.ProductDetailPresenter
import woowacourse.shopping.utils.getSerializableExtraCompat
import woowacourse.shopping.utils.keyError

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var dialogBinding: DialogCartBinding
    private lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ProductDetailPresenter(
            this,
            intent.getSerializableExtraCompat(KEY_PRODUCT) ?: return keyError(KEY_PRODUCT),
            CartDatabase(CartDBHelper(this).writableDatabase),
            RecentProductDatabase(this),
        )
        presenter.setUp()

        binding.cartButton.setOnClickListener {
            presenter.onClickCart()
        }
    }

    override fun setupView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        setSupportActionBar(binding.toolbar)
        dialogBinding = DialogCartBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> finish()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun setProductDetail(product: ProductUIModel) {
        binding.product = product
    }

    override fun navigateToCart() {
        startActivity(CartActivity.from(this))
    }

    override fun showCartDialog(product: ProductUIModel) {
        dialogBinding.product = product
        dialogBinding.tvAddCart.setOnClickListener { presenter.addCart() }
        AlertDialog.Builder(this@ProductDetailActivity).setView(dialogBinding.root).show()
        dialogBinding.customCount.count = presenter.count
        dialogBinding.customCount.plusClickListener = presenter::increaseCount
        dialogBinding.customCount.minusClickListener = presenter::decreaseCount
    }

    override fun onDestroy() {
        super.onDestroy()
        setResult(ACTIVITY_RESULT_CODE)
    }

    companion object {
        const val ACTIVITY_RESULT_CODE = 0
        fun from(context: Context, product: ProductUIModel): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}
