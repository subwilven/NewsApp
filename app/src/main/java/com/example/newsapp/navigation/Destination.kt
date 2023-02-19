package com.example.newsapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.example.newsapp.model.articles.Article
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

        operator fun invoke(articleId: Int): String = route.appendParams(
            ARG_ARTICLE_ID to articleId.toString(),
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

fun navigateToArticleDetails(appNavigator: AppNavigator,article: Article){
    appNavigator.tryNavigateTo(Destination.ArticleDetailsScreen(article.id))
}

fun launchWebView(context: Context,url :String?){
    val webIntent: Intent = Uri.parse(url).let { webpage ->
        Intent(Intent.ACTION_VIEW, webpage)
    }
    ContextCompat.startActivity(context, webIntent, null)
}
