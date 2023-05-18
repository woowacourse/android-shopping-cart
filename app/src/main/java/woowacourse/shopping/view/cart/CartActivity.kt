package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.util.PriceFormatter

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
        binding.checkboxTotal.setOnClickListener {
            presenter.selectAll(binding.checkboxTotal.isChecked)
        }
    }

    private fun setUpBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpPresenter() {
        presenter = CartPresenter(this, CartDbRepository(this), ProductMockRepository)
    }

    override fun showProducts(items: List<CartViewItem>) {
        binding.recyclerCart.adapter = CartAdapter(
            items,
            object : CartAdapter.OnItemClick {
                override fun onRemoveClick(id: Int) {
                    presenter.removeProduct(id)
                }

                override fun onNextClick() {
                    presenter.fetchNextPage()
                }

                override fun onPrevClick() {
                    presenter.fetchPrevPage()
                }

                override fun onUpdateCount(id: Int, count: Int) {
                    presenter.updateCartProductCount(id, count)
                }

                override fun onSelectProduct(product: CartProductModel) {
                    presenter.selectProduct(product)
                }
            }
        )
    }

    override fun showChangedItems() {
        binding.recyclerCart.adapter?.notifyDataSetChanged()
    }

    override fun showChangedItem(position: Int) {
        binding.recyclerCart.adapter?.notifyItemChanged(position)
    }

    override fun showTotalResult(isSelectAll: Boolean, totalPrice: Int, totalCount: Int) {
        binding.checkboxTotal.isChecked = isSelectAll
        binding.textTotalPrice.text = getString(R.string.korean_won, PriceFormatter.format(totalPrice))
        binding.btnOrder.text = getString(R.string.order_count, totalCount)
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
        private const val TITLE = "Cart"
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
