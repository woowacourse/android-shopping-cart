package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityCartBinding

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
        supportActionBar?.title = TITLE
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
            }
        )
    }

    override fun showOtherPage() {
        binding.recyclerCart.adapter?.notifyDataSetChanged()
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
        private const val TITLE = "Cart"
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
