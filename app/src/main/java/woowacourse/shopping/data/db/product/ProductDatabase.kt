package woowacourse.shopping.data.db.product

import woowacourse.shopping.domain.model.Product

object ProductDatabase {
    val products =
        listOf(
            Product(
                id = 0L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-단지(400ml) 레몬청",
            ),
            Product(
                id = 1L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/72c2/0af4/2caf0cd056a7448894e1c0f424483cf8?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=KmEmRkC1tOHZ0SvRrag5S20e6NGzhi5TIEw2CNIEjq7uvG0~Dc0gI4teeJpfMVgslP9IkReydFyE5MQwns11D~0POXZGWqp6cQMk4BIjG0tyAou4kWNtn9glMJZPizXd6NvYJ9QBTYS6WH9ELgGMa8wp6~v9AqvDdDQuCquR2jwDtCd6kqnB5Qx5FlCgpWCQ-Bms1Q324WXky--1Zxwd4ug7yGA0JqeqU~HiT0amjWh6HLk-zB2tJ-G-nhzM1Q49qQDkpF1knozIYpju5x16DUwSW2O6nIrZZFm-IjObGAVN-zBNuv4CC2v0Syjd3iPIz8q7lPZNN2vy~s7VvNytOw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-납작(2000ml) 밀크티",
            ),
            Product(
                id = 2L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/1e9f/f403/c410c794ab4d26aa3aa0c0c2eab7cb36?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=JLgPHKD8pgLS6Fhfw9hjusvszdk0GBM5T7xDDdgyyFbrRe~UcEv-9HrxsDwxdzbWBU~f80Y1kQrt4T2JqDqMVdUeQPNyhcKdca7~k06QPwPy5BQl01IiCCR--OdYwNitwXTr917kkfrdUY6HBeEGMZD~v1MydZqLuGi0~Xwaickjd34pz-5g3JoGmDISNh8nRw0p-Vlb0dUPSgKCkmONZ9bmGX26TpWU~urwZlh6gq7zTu6zDSzNYHhP-4W78rbOZVI~07fWWzlZOhi~rI9W53qI5XeRHkvKikl40tB2v09PpM6mFbqZfVVoFZ0iLWxw8k39bT0YXnLgia0Pn-pFxw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-밀크티(600ml)",
            ),
            Product(
                id = 3L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/0eb1/f5ad/c1d582ec8d002bdeffd2694fd58406e1?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=T-UjPuGXqjT--8lvW653-NpVpa~XYO9TdOBB3BhDr3rV69x482KEYkkpapkyxto13d2FRA9gRrz5oeVhHhsnjy-RSjUOJrsQ9eKuIrZdSC5sUHNCcVNZ2Ro2T4i1zQcYGBxkWCVF9K8ioQhuBYG~uAfhB-CHDz~A6NbWxbUEJr1Jx3JOksNh1wexAovFTCs-eWNx~W-XweqCQuntbZysjrg9375XcwVFrqNrRR1IB~qb~LOl7fN~LEzuKoHjqZGzD6gacetHXjNuHJpjuGckVm-QGocKIfNFNghnZZq-7dF1Lum6GlKpPEMRNuuLgPREzFwlHiPsHkeU6efEjF3bNw__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-정사각형(370ml) 딸기생과일",
            ),
            Product(
                id = 4L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6da6/d855/cc58e3bf0096696a0b9794aa07d4fe3b?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jnMgyopIcrHI5L0XvQ4Ch7rhgZu09-WhBv17k-DWCgkoot8utiBmvfVjcIAaPCSQRhiSKcH8uIBZu8eEdTXNoxBchIjilqyR8t99gqzSMkX1cE-UOGNNgyDPY4gh0IXExwPxvWO3mB4qsnaMWssy3cW3M6m0CvAxMYu5BTryU839YcaCPEpf6wbcr-TUzdsLJcZXfPH2W9mxAGY2LEjJbZgtdfdJvHuC5A97CX2Yb9Q~KB3QlUnuIk55KbUx7fVdyLY45RBLeXG6bESLRAGTaCr0QZbeo7aNFpxtYxoyQNQxSU-~cw-r2riC7kHUN~GgNPKC~wFL31HZDZkWY9c8rw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-단지(260ml) 초록밀크티",
            ),
            Product(
                id = 5L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-정사각형(500ml) 딸기생과일",
            ),
            Product(
                id = 6L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 20_000,
                name = "PET보틀-단지(800ml) 수제 레몬청",
            ),
            Product(
                id = 7L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6da6/d855/cc58e3bf0096696a0b9794aa07d4fe3b?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jnMgyopIcrHI5L0XvQ4Ch7rhgZu09-WhBv17k-DWCgkoot8utiBmvfVjcIAaPCSQRhiSKcH8uIBZu8eEdTXNoxBchIjilqyR8t99gqzSMkX1cE-UOGNNgyDPY4gh0IXExwPxvWO3mB4qsnaMWssy3cW3M6m0CvAxMYu5BTryU839YcaCPEpf6wbcr-TUzdsLJcZXfPH2W9mxAGY2LEjJbZgtdfdJvHuC5A97CX2Yb9Q~KB3QlUnuIk55KbUx7fVdyLY45RBLeXG6bESLRAGTaCr0QZbeo7aNFpxtYxoyQNQxSU-~cw-r2riC7kHUN~GgNPKC~wFL31HZDZkWY9c8rw__
                    """.trimIndent(),
                price = 18_000,
                name = "PET보틀-단지(500ml) 초록밀크티",
            ),
            Product(
                id = 8L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 5_000,
                name = "PET보틀-단지(200ml) 레몬청",
            ),
            Product(
                id = 9L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__
                    """.trimIndent(),
                price = 30_000,
                name = "PET보틀-정사각형(1000ml) 딸기생과일",
            ),
            Product(
                id = 10L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-단지(400ml) 레몬청",
            ),
            Product(
                id = 11L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/72c2/0af4/2caf0cd056a7448894e1c0f424483cf8?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=KmEmRkC1tOHZ0SvRrag5S20e6NGzhi5TIEw2CNIEjq7uvG0~Dc0gI4teeJpfMVgslP9IkReydFyE5MQwns11D~0POXZGWqp6cQMk4BIjG0tyAou4kWNtn9glMJZPizXd6NvYJ9QBTYS6WH9ELgGMa8wp6~v9AqvDdDQuCquR2jwDtCd6kqnB5Qx5FlCgpWCQ-Bms1Q324WXky--1Zxwd4ug7yGA0JqeqU~HiT0amjWh6HLk-zB2tJ-G-nhzM1Q49qQDkpF1knozIYpju5x16DUwSW2O6nIrZZFm-IjObGAVN-zBNuv4CC2v0Syjd3iPIz8q7lPZNN2vy~s7VvNytOw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-납작(2000ml) 밀크티",
            ),
            Product(
                id = 12L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/1e9f/f403/c410c794ab4d26aa3aa0c0c2eab7cb36?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=JLgPHKD8pgLS6Fhfw9hjusvszdk0GBM5T7xDDdgyyFbrRe~UcEv-9HrxsDwxdzbWBU~f80Y1kQrt4T2JqDqMVdUeQPNyhcKdca7~k06QPwPy5BQl01IiCCR--OdYwNitwXTr917kkfrdUY6HBeEGMZD~v1MydZqLuGi0~Xwaickjd34pz-5g3JoGmDISNh8nRw0p-Vlb0dUPSgKCkmONZ9bmGX26TpWU~urwZlh6gq7zTu6zDSzNYHhP-4W78rbOZVI~07fWWzlZOhi~rI9W53qI5XeRHkvKikl40tB2v09PpM6mFbqZfVVoFZ0iLWxw8k39bT0YXnLgia0Pn-pFxw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-밀크티(600ml)",
            ),
            Product(
                id = 13L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/0eb1/f5ad/c1d582ec8d002bdeffd2694fd58406e1?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=T-UjPuGXqjT--8lvW653-NpVpa~XYO9TdOBB3BhDr3rV69x482KEYkkpapkyxto13d2FRA9gRrz5oeVhHhsnjy-RSjUOJrsQ9eKuIrZdSC5sUHNCcVNZ2Ro2T4i1zQcYGBxkWCVF9K8ioQhuBYG~uAfhB-CHDz~A6NbWxbUEJr1Jx3JOksNh1wexAovFTCs-eWNx~W-XweqCQuntbZysjrg9375XcwVFrqNrRR1IB~qb~LOl7fN~LEzuKoHjqZGzD6gacetHXjNuHJpjuGckVm-QGocKIfNFNghnZZq-7dF1Lum6GlKpPEMRNuuLgPREzFwlHiPsHkeU6efEjF3bNw__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-정사각형(370ml) 딸기생과일",
            ),
            Product(
                id = 14L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6da6/d855/cc58e3bf0096696a0b9794aa07d4fe3b?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jnMgyopIcrHI5L0XvQ4Ch7rhgZu09-WhBv17k-DWCgkoot8utiBmvfVjcIAaPCSQRhiSKcH8uIBZu8eEdTXNoxBchIjilqyR8t99gqzSMkX1cE-UOGNNgyDPY4gh0IXExwPxvWO3mB4qsnaMWssy3cW3M6m0CvAxMYu5BTryU839YcaCPEpf6wbcr-TUzdsLJcZXfPH2W9mxAGY2LEjJbZgtdfdJvHuC5A97CX2Yb9Q~KB3QlUnuIk55KbUx7fVdyLY45RBLeXG6bESLRAGTaCr0QZbeo7aNFpxtYxoyQNQxSU-~cw-r2riC7kHUN~GgNPKC~wFL31HZDZkWY9c8rw__
                    """.trimIndent(),
                price = 12_000,
                name = "PET보틀-단지(260ml) 초록밀크티",
            ),
            Product(
                id = 15L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__
                    """.trimIndent(),
                price = 10_000,
                name = "PET보틀-정사각형(500ml) 딸기생과일",
            ),
            Product(
                id = 16L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 20_000,
                name = "PET보틀-단지(800ml) 수제 레몬청",
            ),
            Product(
                id = 17L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6da6/d855/cc58e3bf0096696a0b9794aa07d4fe3b?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jnMgyopIcrHI5L0XvQ4Ch7rhgZu09-WhBv17k-DWCgkoot8utiBmvfVjcIAaPCSQRhiSKcH8uIBZu8eEdTXNoxBchIjilqyR8t99gqzSMkX1cE-UOGNNgyDPY4gh0IXExwPxvWO3mB4qsnaMWssy3cW3M6m0CvAxMYu5BTryU839YcaCPEpf6wbcr-TUzdsLJcZXfPH2W9mxAGY2LEjJbZgtdfdJvHuC5A97CX2Yb9Q~KB3QlUnuIk55KbUx7fVdyLY45RBLeXG6bESLRAGTaCr0QZbeo7aNFpxtYxoyQNQxSU-~cw-r2riC7kHUN~GgNPKC~wFL31HZDZkWY9c8rw__
                    """.trimIndent(),
                price = 18_000,
                name = "PET보틀-단지(500ml) 초록밀크티",
            ),
            Product(
                id = 18L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__
                    """.trimIndent(),
                price = 5_000,
                name = "PET보틀-단지(200ml) 레몬청",
            ),
            Product(
                id = 19L,
                imageUrl =
                    """
                    https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__
                    """.trimIndent(),
                price = 30_000,
                name = "PET보틀-정사각형(1000ml) 딸기생과일",
            ),
        )
}
