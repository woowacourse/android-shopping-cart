package woowacourse.shopping.presentation.base

import android.database.SQLException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonParseException
import java.io.IOException
import java.util.concurrent.TimeoutException

abstract class BaseViewModel : ViewModel() {
    private val _message: MutableLiveData<Event<MessageProvider>> = MutableLiveData()
    val message: LiveData<Event<MessageProvider>> get() = _message

    private val _error: MutableLiveData<ErrorState?> = MutableLiveData(null)
    val error: LiveData<ErrorState?> get() = _error

    abstract fun retry()

    fun showMessage(messageProvider: MessageProvider) {
        _message.emit(messageProvider)
    }

    private fun handleError(
        e: Throwable,
        onUnhandledError: () -> Unit = {},
    ) {
        when (e) {
            is IllegalArgumentException -> showError("잘못된 요청", "잘못된 요청입니다.")
            is SecurityException -> showError("보안 오류", "인증 오류가 발생했습니다.")
            is NoSuchElementException -> showError("요소 없음", "요청한 요소를 찾을 수 없습니다.")
            is IOException -> showError("서버 오류", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
            is TimeoutException -> showError("시간 초과 오류", "작업 시간이 초과되었습니다. 다시 시도해주세요.")
            is SQLException -> showError("데이터베이스 오류", "데이터베이스에 접근하는 중 오류가 발생했습니다.")
            is JsonParseException -> showError("JSON 파싱 오류", "데이터를 처리하는 중 오류가 발생했습니다.")
            else -> {
                if (onUnhandledError == {}) {
                    showError("알 수 없는 오류", "알 수 없는 오류가 발생했습니다. 다시 시도해주세요.")
                } else {
                    onUnhandledError()
                }
            }
        }
    }

    fun showError(e: Throwable) {
        handleError(e)
    }

    fun showError(
        title: String,
        description: String,
    ) {
        val errorState = ErrorState(title = title, description = description)
        _error.postValue(errorState)
    }

    fun hideError() {
        _error.postValue(null)
    }
}
