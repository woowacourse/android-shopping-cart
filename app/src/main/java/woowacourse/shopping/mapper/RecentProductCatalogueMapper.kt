package woowacourse.shopping.mapper

import com.shopping.domain.MainProductCatalogue
import com.shopping.domain.RecentProductCatalogue
import woowacourse.shopping.RecentProductCatalogueUIModel
import woowacourse.shopping.toDomain
import woowacourse.shopping.toUIModel

fun RecentProductCatalogueUIModel.toDomain() =
    RecentProductCatalogue(mainProductCatalogue.toDomain())

fun RecentProductCatalogue.toUIModel() =
    RecentProductCatalogueUIModel((productCatalogue as MainProductCatalogue).toUIModel())
