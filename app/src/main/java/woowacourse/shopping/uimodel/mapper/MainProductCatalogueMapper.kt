package woowacourse.shopping

import com.shopping.domain.MainProductCatalogue
import woowacourse.shopping.uimodel.MainProductCatalogueUIModel
import woowacourse.shopping.uimodel.mapper.toDomain
import woowacourse.shopping.uimodel.mapper.toUIModel

fun MainProductCatalogueUIModel.toDomain(): MainProductCatalogue =
    MainProductCatalogue(items.map { it.toDomain() })

fun MainProductCatalogue.toUIModel(): MainProductCatalogueUIModel =
    MainProductCatalogueUIModel(items.map { it.toUIModel() })
