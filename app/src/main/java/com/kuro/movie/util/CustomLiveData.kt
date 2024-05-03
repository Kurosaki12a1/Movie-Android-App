package com.kuro.movie.util

import androidx.lifecycle.LiveData

class CustomLiveData<T>(value: T) : LiveData<T>() {

    public override fun setValue(newValue: T) {
        if (this.value != newValue) {
            super.setValue(newValue)
        }
    }

    public override fun postValue(newValue: T) {
        if (this.value != newValue) {
            super.postValue(value)
        }
    }
}