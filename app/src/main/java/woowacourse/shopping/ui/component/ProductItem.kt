@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun ProductImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    bottomHoverContent: @Composable () -> Unit = {},
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.product_image_content_description, contentDescription),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(154.dp)
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
        )
        Box(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp),
        ) {
            bottomHoverContent()
        }
    }
}

@Composable
fun ProductItem(
    title: String,
    price: String,
    productImage: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        productImage()
        Text(
            text = title,
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
            text = price,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier =
                Modifier.padding(
                    horizontal = 7.5.dp,
                ),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductItemPreview() {
    AndroidShoppingTheme {
        ProductItem(
            title = "동원 스위트콘",
            price = "엄청 비싼 가격",
            productImage = {
                ProductImage(
                    imageUrl = "",
                    contentDescription = "동원 스위트콘",
                    bottomHoverContent = { Text("Hello") },
                )
            },
        )
    }
}
