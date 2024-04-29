package com.kuro.movie.data.repository.data_source.remote

import android.content.Context
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.data_source.remote.FirebaseTvSeriesRepository
import com.kuro.movie.util.Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME
import com.kuro.movie.util.Constants.FIREBASE_TV_SERIES_FIELD_NAME
import com.kuro.movie.util.Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeCallWithForFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseTvSeriesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val firestore: FirebaseFirestore,
) : FirebaseTvSeriesRepository {
    override suspend fun getFavoriteTvSeries(userUid: String): Resource<List<TvSeries>> {
        return safeCallWithForFirebase(context) {
            val result =
                firestore.collection(userUid).document(FIREBASE_FAVORITE_TV_DOCUMENT_NAME).get()
                    .await()
            val tvSeries = documentToListTvSeries(document = result)

            if (tvSeries.isNotEmpty()) {
                Resource.Success(tvSeries)
            } else {
                Resource.failure(Exception("No data found"))
            }
        }
    }

    override suspend fun getTvSeriesInWatchList(userUid: String): Resource<List<TvSeries>> {
        return safeCallWithForFirebase(context) {
            val result =
                firestore.collection(userUid).document(FIREBASE_TV_WATCH_DOCUMENT_NAME).get()
                    .await()
            val tvSeries = documentToListTvSeries(document = result)

            if (tvSeries.isNotEmpty()) {
                Resource.Success(tvSeries)
            } else {
                Resource.failure(Exception("No data found"))
            }
        }
    }

    private fun documentToListTvSeries(
        document: DocumentSnapshot
    ): List<TvSeries> {
        return try {
            val mapOfTvSeries = document.get(FIREBASE_TV_SERIES_FIELD_NAME) as List<*>

            val listOfTvSeries = mapOfTvSeries.map {
                it as Map<*, *>
                val data = it["tvSeries"] as Map<*, *>
                val overview = data["overview"] as String
                val voteAverage = data["voteAverage"] as Double
                val firstAirDate = data["firstAirDate"] as String
                val name = data["name"] as String
                val voteCountByString = data["formattedVoteCount"] as String
                val genreByOne = data["genreByOne"] as String
                val id = data["id"] as Number
                val genreIds = data["genreIds"] as List<*>
                val posterPath = data["posterPath"] as String

                val movie = TvSeries(
                    id = id.toInt(),
                    overview = overview,
                    name = name,
                    posterPath = posterPath,
                    firstAirDate = firstAirDate,
                    genreIds = genreIds.map { it.toString().toInt() },
                    formattedVoteCount = voteCountByString,
                    genreByOne = genreByOne,
                    voteAverage = voteAverage
                )
                movie
            }
            listOfTvSeries
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}