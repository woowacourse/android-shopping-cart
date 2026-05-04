@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.ui.ProductDto

@Composable
fun ProductItem(
    product: ProductDto,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = stringResource(R.string.product_image_content_description, product.title),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(154.dp)
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
        )
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            modifier =
                Modifier.padding(
                    horizontal = 7.5.dp,
                ),
        )
        Text(
            text = product.price,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier =
                Modifier.padding(
                    horizontal = 7.5.dp,
                ),
        )
    }
}
