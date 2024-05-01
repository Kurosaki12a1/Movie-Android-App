package kuro.pratice.imagepicker.presenter

import kuro.pratice.imagepicker.data.entity.Album

interface ImagePickerListener {

    fun onTakePhoto() {}

    fun onAlbumClick(album: Album, position: Int) {}
}