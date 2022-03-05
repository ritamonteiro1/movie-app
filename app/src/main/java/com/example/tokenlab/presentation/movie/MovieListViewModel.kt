package com.example.tokenlab.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.use_case.GetMovieListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieListViewModel(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getMovieList() {
        viewModelScope.launch(dispatcher) {
            _loading.postValue(true)
            try {
                val movies = getMovieListUseCase.getMovieList()
                _loading.postValue(false)
                _movieList.postValue(movies)
            } catch (e: Exception) {
                _loading.postValue(false)
                _error.postValue(e)
            }
        }
    }
}