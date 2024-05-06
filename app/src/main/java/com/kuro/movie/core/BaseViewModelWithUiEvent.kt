package com.kuro.movie.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kuro.movie.util.postUpdate

/**
 * Abstract ViewModel class for handling UI events in a consumable manner.
 * This class provides a structure to manage a list of UI events which should only be consumed once,
 * such as navigation's or showing snack bars, ensuring that events are not handled multiple times
 * due to configuration changes like screen rotations.
 */
abstract class BaseViewModelWithUiEvent<T> : BaseViewModel() {

    // LiveData holding a list of events. Initially, this list is empty.
    private val _consumableViewEvents = MutableLiveData<List<T>>(emptyList())

    // Publicly exposed LiveData, allowing the view (Fragment or Activity) to observe and react to the events.
    val consumableViewEvents: LiveData<List<T>>
        get() = _consumableViewEvents

    /**
     * Adds a new event to the current list of consumable UI events.
     * This function appends the event to the end of the existing list and posts the updated list to the LiveData.
     *
     * @param uiEvent the event to add to the list of consumable events.
     */
    protected fun addConsumableViewEvent(uiEvent: T) {
        _consumableViewEvents.postUpdate { it.plus(uiEvent) }
    }

    /**
     * Removes the first event from the list of consumable UI events, indicating that the event has been consumed.
     * This function is typically called after the event has been handled to ensure it is not handled again.
     */
    fun onEventConsumed() {
        val remainingEvents = _consumableViewEvents.value.orEmpty().toMutableList()
        if (remainingEvents.isNotEmpty()) {
            remainingEvents.removeFirst()
        }
        _consumableViewEvents.postValue(remainingEvents)
    }
}