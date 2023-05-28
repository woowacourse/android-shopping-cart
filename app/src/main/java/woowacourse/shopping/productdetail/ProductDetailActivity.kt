package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.database.cart.CartDao
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.recentProduct.RecentProductDao
import woowacourse.shopping.database.recentProduct.RecentRepositoryImpl
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
        val product: ProductUIModel =
            intent.getSerializableExtraCompat(KEY_PRODUCT) ?: return keyError(KEY_PRODUCT)
        presenter = ProductDetailPresenter(
            this,
            product,
            CartRepositoryImpl(CartDao(this)),
            RecentRepositoryImpl(RecentProductDao(this)),
        )
        presenter.setUp()
        presenter.manageRecentView()
        binding.cartButton.setOnClickListener {
            presenter.onClickCart()
        }
        binding.recentProductLayout.setOnClickListener {
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(KEY_PRODUCT, product)
            intent.flags = FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
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
        presenter.setUpCountView()
    }

    override fun setUpCountView() {
        dialogBinding.customCount.plusClickListener = presenter::increaseCount
        dialogBinding.customCount.minusClickListener = presenter::decreaseCount
    }

    override fun setCount(count: Int) {
        dialogBinding.customCount.count = presenter.count
    }

    override fun disappearRecent() {
        binding.recentProductLayout.visibility = View.GONE
    }

    override fun displayRecent(product: ProductUIModel) {
        binding.recentProduct = product
        binding.recentProductLayout.visibility = View.VISIBLE
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
