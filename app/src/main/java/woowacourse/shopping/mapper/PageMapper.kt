package woowacourse.shopping.mapper

import woowacourse.shopping.domain.model.page.DomainPage
import woowacourse.shopping.model.UiPage

fun DomainPage.toUi(): UiPage = UiPage(value = value)
