package com.example.newsapp.navigation

import com.example.newsapp.util.ARG_ARTICLE_ID

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object ArticlesScreen : NoArgumentsDestination("articles")

    object FavoritesScreen : NoArgumentsDestination("favorites")

    object ArticleDetailsScreen : Destination("article_details", ARG_ARTICLE_ID) {

        operator fun invoke(articleId: String): String = route.appendParams(
            ARG_ARTICLE_ID to articleId,
        )
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}