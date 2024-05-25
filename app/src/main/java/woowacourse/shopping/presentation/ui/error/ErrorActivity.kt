package woowacourse.shopping.presentation.ui.error

import android.content.Context
import android.content.Intent
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityErrorBinding
import woowacourse.shopping.presentation.base.BaseActivity

class ErrorActivity : BaseActivity<ActivityErrorBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_error

    override fun initCreateView() {
        val title = intent.getStringExtra(PUT_EXTRA_TITLE)
        val description = intent.getStringExtra(PUT_EXTRA_DESCRIPTION)

        binding.errorTitle.text = title
        binding.errorDescription.text = description
        binding.retryButton.setOnClickListener { finish() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    companion object {
        private const val PUT_EXTRA_TITLE = "title"
        private const val PUT_EXTRA_DESCRIPTION = "description"

        fun getIntent(
            context: Context,
            title: String,
            description: String,
        ): Intent {
            return Intent(context, ErrorActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(PUT_EXTRA_TITLE, title)
                putExtra(PUT_EXTRA_DESCRIPTION, description)
            }
        }
    }
}
