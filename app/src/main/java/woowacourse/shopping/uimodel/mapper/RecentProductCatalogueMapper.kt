package woowacourse.shopping.uimodel.mapper

import com.shopping.domain.MainProductCatalogue
import com.shopping.domain.RecentProductCatalogue
import woowacourse.shopping.toDomain
import woowacourse.shopping.toUIModel
import woowacourse.shopping.uimodel.RecentProductCatalogueUIModel

fun RecentProductCatalogueUIModel.toDomain() =
    RecentProductCatalogue(mainProductCatalogue.toDomain())

fun RecentProductCatalogue.toUIModel() =
    RecentProductCatalogueUIModel((productCatalogue as MainProductCatalogue).toUIModel())
