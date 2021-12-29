package com.example.tokenlab.presentation.movie

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenlab.domain.exception.GenericErrorException
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

    private val _networkError = MutableLiveData<Unit>()
    val networkError: LiveData<Unit> = _networkError

    private val _genericError = MutableLiveData<Unit>()
    val genericError: LiveData<Unit> = _genericError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getMovieList() {
        viewModelScope.launch(dispatcher) {
            _loading.postValue(true)
            try {
                val movies = getMovieListUseCase.getMovieList()
                _loading.postValue(false)
                _movieList.postValue(movies)
            } catch (e: NetworkErrorException) {
                _loading.postValue(false)
                _networkError.postValue(Unit)
            } catch (e: GenericErrorException) {
                _loading.postValue(false)
                _genericError.postValue(Unit)
            } catch (e: Exception){
                _loading.postValue(false)
                _genericError.postValue(Unit)
            }
        }
    }
}