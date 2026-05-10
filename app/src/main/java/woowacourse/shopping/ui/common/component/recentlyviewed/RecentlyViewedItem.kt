package woowacourse.shopping.ui.common.component.recentlyviewed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

@Composable
fun RecentlyViewedItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .width(98.dp)
                .height(121.dp)
                .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier.size(98.dp),
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = stringResource(R.string.content_description_image),
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(Modifier.size(6.dp))
        Text(
            text = product.name,
            color = Color.Black,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp, end = 9.dp),
        )
    }
}

@Preview
@Composable
fun RecentlyViewedItemPreview() {
    RecentlyViewedItem(
        product = InMemoryProductRepository.APPLE,
        onClick = {},
    )
}
