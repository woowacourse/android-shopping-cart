package woowacourse.shopping.error

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorBinding

    private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>(EXTRA_INTENT) }
    private val errorText by lazy { intent.getStringExtra(EXTRA_ERROR_TEXT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = ActivityErrorBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_error)

        binding.tvErrorLog.text = errorText

        binding.btnReload.setOnClickListener {
            startActivity(lastActivityIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_INTENT = "EXTRA_INTENT"
        const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"

        fun getIntent(context: Context, intent: Intent, errorText: String): Intent {
            return Intent(context, ErrorActivity::class.java).apply {
                putExtra(EXTRA_INTENT, intent)
                putExtra(EXTRA_ERROR_TEXT, errorText)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        }
    }
}
