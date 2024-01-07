package com.poc.photosapp.di

import java.io.IOException

class NetworkConnectivityException(val errorMessage: String) :
    IOException() {
}