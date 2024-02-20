package com.example.newsapp.util

object Constants {

    object Defaults {

        const val BASE_URL = "https://newsapi.org/v2/"
        const val PAGE_SIZE = 15
        const val INITIAL_PAGE_MULTIPLIER = 2
        const val START_PAGE_NUMBER = 1
        const val COUNTRY = "us"
        const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val DEBOUNCE_SEARCH_INPUT = 400L
        const val DELAY_DUMMY_LOADING = 1500L
        const val FLOW_SUBSCRIPTION_TIMEOUT = 5_000L
    }

    object Args {

        const val ARTICLE_ID = "articleId"
    }

    object Destinations {

        const val ARTICLES = "articles"
        const val FAVORITES = "favorites"
        const val ARTICLE_DETAILS = "article_details"
        const val PROVIDERS = "providers"

    }
}

