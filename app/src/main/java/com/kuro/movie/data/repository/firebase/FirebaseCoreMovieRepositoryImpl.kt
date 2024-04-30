package com.kuro.movie.data.repository.firebase

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kuro.movie.data.mapper.toFavoriteMovie
import com.kuro.movie.data.mapper.toMovieWatchListItem
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.entity.movie.FavoriteMovie
import com.kuro.movie.data.model.entity.movie.MovieWatchListItem
import com.kuro.movie.domain.repository.data_source.remote.FirebaseCoreMovieRepository
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME
import com.kuro.movie.util.Constants.FIREBASE_MOVIES_FIELD_NAME
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeCallWithForFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCoreMovieRepositoryImpl @Inject constructor(
    private val context: Context,
    private val fireStore: FirebaseFirestore
) : FirebaseCoreMovieRepository {
    override suspend fun addMovieToFavoriteList(
        userUid: String,
        movieInFavoriteList: List<Movie>
    ): Resource<Unit> {
        return safeCallWithForFirebase(context) {
            val data = mapOf(FIREBASE_MOVIES_FIELD_NAME to movieInFavoriteList)
            val task = fireStore.collection(userUid)
                .document(FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
                .set(data.toConvertToFavoriteMovieMap(), SetOptions.merge())
            task.await()
            if (task.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.failure(Exception("Failed to add Movie"))
            }
        }
    }

    override suspend fun addMovieToWatchList(
        userUid: String,
        moviesInWatchList: List<Movie>
    ): Resource<Unit> {
        return safeCallWithForFirebase(context) {
            val data = mapOf(FIREBASE_MOVIES_FIELD_NAME to moviesInWatchList)
            val result = fireStore.collection(userUid)
                .document(Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME)
                .set(data.toConvertToMovieWatchMap(), SetOptions.merge())
            result.await()
            if (result.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.failure(Exception("Failed to add Movie"))
            }
        }
    }

    private fun Map<String, List<Movie>>.toConvertToFavoriteMovieMap(): Map<String, List<FavoriteMovie>> {
        return this.mapValues { it.value.map { it.toFavoriteMovie() } }
    }

    private fun Map<String, List<Movie>>.toConvertToMovieWatchMap(): Map<String, List<MovieWatchListItem>> {
        return this.mapValues {
            it.value.map { it.toMovieWatchListItem() }
        }
    }
}