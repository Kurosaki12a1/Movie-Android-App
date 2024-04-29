package com.kuro.movie.data.repository.data_source.remote

import android.content.Context
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.data_source.remote.FirebaseMovieRepository
import com.kuro.movie.extension.orZero
import com.kuro.movie.util.Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME
import com.kuro.movie.util.Constants.FIREBASE_MOVIES_FIELD_NAME
import com.kuro.movie.util.Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeCallWithForFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseMovieRepositoryImpl @Inject constructor(
    private val context: Context,
    private val firestore: FirebaseFirestore,
) : FirebaseMovieRepository {

    override suspend fun getFavoriteMovie(userUid: String): Resource<List<Movie>> {
        return safeCallWithForFirebase(context) {
            val result =
                firestore.collection(userUid).document(FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
                    .get()
                    .await()

            val movies = documentToListMovie(document = result)

            if (movies.isNotEmpty()) {
                Resource.Success(movies)
            } else {
                Resource.failure(Exception("No data found"))
            }
        }
    }

    override suspend fun getMovieInWatchList(userUid: String): Resource<List<Movie>> {
        return safeCallWithForFirebase(context) {
            val result =
                firestore.collection(userUid).document(FIREBASE_MOVIE_WATCH_DOCUMENT_NAME).get()
                    .await()
            val movies = documentToListMovie(document = result)

            if (movies.isNotEmpty()) {
                Resource.Success(movies)
            } else {
                Resource.failure(Exception("No data found"))
            }
        }
    }

    private fun documentToListMovie(
        document: DocumentSnapshot,
    ): List<Movie> {
        try {
            val mapOfMovies = document.get(FIREBASE_MOVIES_FIELD_NAME) as List<*>
            val listOfMovie = mapOfMovies.map {
                it as Map<*, *>
                val data = it["movie"] as? Map<*, *>
                val overview = data?.get("overview") as? String
                val voteAverage = data?.get("voteAverage") as? Double
                val releaseDate = data?.get("releaseDate") as? String
                val genresBySeparatedByComma =
                    data?.get("genresBySeparatedByComma") as? String
                val voteCountByString = data?.get("fullReleaseDate") as? String
                val genreByOne = data?.get("genreByOne") as? String
                val id = data?.get("id") as? Number
                val genreIds = data?.get("genreIds") as? List<*>
                val title = data?.get("title") as? String
                val posterPath = data?.get("posterPath") as? String
                val favorite = data?.get("favorite") as? Boolean

                val movie = Movie(
                    id = id?.toInt().orZero(),
                    overview = overview.orEmpty(),
                    title = title.orEmpty(),
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    genreIds = genreIds?.map { it.toString().toInt() }.orEmpty(),
                    formattedVoteCount = voteCountByString.orEmpty(),
                    genreByOne = genreByOne.orEmpty(),
                    genresBySeparatedByComma = genresBySeparatedByComma.orEmpty(),
                    voteAverage = voteAverage.orZero(),
                    isFavorite = favorite ?: false
                )
                movie
            }
            return listOfMovie
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}