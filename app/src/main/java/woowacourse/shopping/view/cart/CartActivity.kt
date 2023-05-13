package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartPageStatus
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.model.ProductModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)
        setUpActionBar()
        setUpPresenter()
        presenter.fetchProducts()
    }

    private fun setUpBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
    }

    private fun setUpPresenter() {
        presenter = CartPresenter(this, CartDbRepository(this), ProductMockRepository)
    }

    override fun showProducts(
        cartProducts: List<CartProductModel>,
        cartPageStatus: CartPageStatus
    ) {
        binding.recyclerCart.adapter = CartAdapter(cartProducts, object : CartAdapter.OnItemClick {
            override fun onRemoveClick(id: Int) {
                presenter.removeProduct(id)
            }

            override fun onNextClick() {
                presenter.fetchNextPage()
            }

            override fun onPrevClick() {
                presenter.fetchPrevPage()
            }
        }, cartPageStatus)
    }

    override fun showOtherPage(size: Int) {
        binding.recyclerCart.adapter?.notifyItemRangeChanged(0, size + 1)
    }

    override fun notifyRemoveItem(position: Int) {
        binding.recyclerCart.adapter?.notifyItemRemoved(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
