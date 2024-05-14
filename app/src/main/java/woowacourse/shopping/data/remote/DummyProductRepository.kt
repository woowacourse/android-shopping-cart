package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.Product

class DummyProductRepository : ProductRepository {
    val products: List<Product> =
        listOf(
            Product(
                id = 0,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 1,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 2,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 3,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 4,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 5,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 6,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 7,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 8,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 9,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 10,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 11,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 12,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 13,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
            Product(
                id = 14,
                imgUrl = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                name = "PET보틀-정사각(370ml)",
                price = 10000,
            ),
        )

    override fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        runCatching {
            val startIndex = pageOffset * pageSize
            val endIndex = if (products.size < startIndex * pageSize) startIndex * pageSize else products.size
            products.subList(startIndex, endIndex)
        }
}
