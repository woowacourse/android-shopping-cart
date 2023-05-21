package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductsAdapter
import woowacourse.shopping.feature.list.item.CartProductItem

class CartActivity : AppCompatActivity(), CartActivityContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartProductsAdapter
    override lateinit var presenter: CartActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = CartDbHandler(CartDbHelper(this).writableDatabase)
        presenter = CartActivityPresenter(this, db)
        presenter.setUpButton()
        presenter.setUpData()
    }

    override fun setUpRecyclerView(cartItems: List<CartProductItem>) {
        adapter = CartProductsAdapter(
            onDeleteItem = { item -> onDeleteItem(item) },
        )
        binding.cartProductRv.adapter = adapter
        adapter.setItems(cartItems)
    }

    override fun setButtonListener(maxPage: Int) {
        binding.pageAfterTv.setOnClickListener {
            presenter.nextPage()
        }

        binding.pageBeforeTv.setOnClickListener {
            presenter.previousPage()
        }
    }

    override fun updateButtonsEnabledState(page: Int, maxPage: Int) {
        binding.pageBeforeTv.isEnabled = page > 1
        binding.pageAfterTv.isEnabled = page < maxPage
    }

    override fun updateAdapterData(cartItems: List<CartProductItem>) {
        adapter.setItems(cartItems)
    }

    override fun setPage(page: Int) {
        binding.pageNumberTv.text = "$page"
    }

    private fun onDeleteItem(item: CartProductItem) {
        presenter.deleteData(item)
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
