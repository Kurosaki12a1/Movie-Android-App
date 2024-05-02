package com.kuro.movie.data.repository.firebase

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kuro.movie.data.mapper.toFavoriteTvSeries
import com.kuro.movie.data.mapper.toTvSeriesWatchListItem
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.data.model.entity.tvseries.FavoriteTvSeries
import com.kuro.movie.data.model.entity.tvseries.TvSeriesWatchListItem
import com.kuro.movie.domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Constants.FIREBASE_TV_SERIES_FIELD_NAME
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeCallWithForFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCoreTvSeriesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val fireStore: FirebaseFirestore
) : FirebaseCoreTvSeriesRepository {
    override suspend fun addTvSeriesToFavoriteList(
        userUid: String,
        tvSeriesInFavoriteList: List<TvSeries>
    ): Resource<Unit> {
        return safeCallWithForFirebase(context) {
            val data = mapOf(FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInFavoriteList)
            val result = fireStore.collection(userUid)
                .document(Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME)
                .set(data.toConvertToFavoriteTvSeriesMap(), SetOptions.merge())
            result.await()
            if (result.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.failure(Exception("Failed to add Tv Series to Favorite List"))
            }
        }
    }

    override suspend fun addTvSeriesToWatchList(
        userUid: String,
        tvSeriesInWatchList: List<TvSeries>
    ): Resource<Unit> {
        return safeCallWithForFirebase(context) {
            val data = mapOf(FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInWatchList)
            val result = fireStore.collection(userUid)
                .document(Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME)
                .set(data.toConvertToTvSeriesWatchMap(), SetOptions.merge())
            result.await()
            if (result.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.failure(Exception("Failed to add Tv Series to Watch List"))
            }
        }
    }

    private fun Map<String, List<TvSeries>>.toConvertToFavoriteTvSeriesMap(): Map<String, List<FavoriteTvSeries>> {
        return this.mapValues { it.value.map { it.toFavoriteTvSeries() } }
    }

    private fun Map<String, List<TvSeries>>.toConvertToTvSeriesWatchMap(): Map<String, List<TvSeriesWatchListItem>> {
        return this.mapValues { it.value.map { it.toTvSeriesWatchListItem() } }
    }
}