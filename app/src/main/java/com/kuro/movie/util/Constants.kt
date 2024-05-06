package com.kuro.movie.util

object Constants {
    const val ITEMS_PER_PAGE = 20
    const val STARTING_PAGE = 1

    const val SPLASH_SCREEN_DELAY = 2000L

    const val MESSAGE_SEND_EMAIL_SUCCESSFUL = "Check your email"

    const val FAVORITE_MOVIE_TABLE_NAME = "favoriteMovieTable"
    const val FAVORITE_TV_SERIES_TABLE_NAME = "favoriteTvSeriesTable"

    const val MOVIE_WATCH_LIST_ITEM_TABLE_NAME = "movieWatchListTable"
    const val TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME = "tvSeriesWatchListTable"

    const val UPCOMING_REMIND_TABLE_NAME = "upcoming_remind"

    const val QUERY_APPEND_TO_RESPONSE = "credits,watch/providers"

    const val FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME = "FavoriteMovies"
    const val FIREBASE_FAVORITE_TV_DOCUMENT_NAME = "FavoriteTvSeries"
    const val FIREBASE_MOVIE_WATCH_DOCUMENT_NAME = "MovieWatchList"
    const val FIREBASE_TV_WATCH_DOCUMENT_NAME = "TvSeriesWatchList"


    const val FIREBASE_MOVIES_FIELD_NAME = "movies"
    const val FIREBASE_TV_SERIES_FIELD_NAME = "tvSeries"

    const val DEFAULT_LANGUAGE = "en"
    const val DEFAULT_REGION = "US"

    const val TIMEOUT_BACK_PRESS = 2000L
    const val PROVIDER_FIREBASE_ACCOUNT = "password"

    const val AVATAR_SIZE = 100f
    const val BATCH_SIZE = 10

    const val DISCOVER_DATE_QUERY_FOR_TV = "first_air_date"

    const val DETAIL_DEFAULT_ID = 0

    const val TYPE_TRAILER = "Trailer"
    const val TYPE_TEASER = "Teaser"

    const val TMDB_MOVIE_URL = "https://www.themoviedb.org/movie/"
    const val TMDB_TV_URL = "https://www.themoviedb.org/tv/"

    const val HOUR_KEY = "hour"
    const val MINUTES_KEY = "minutes"

    const val TV_SERIES_STATUS_ENDED = "Ended"
}