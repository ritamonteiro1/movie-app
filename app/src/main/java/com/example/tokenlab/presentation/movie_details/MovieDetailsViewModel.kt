package com.example.tokenlab.presentation.movie_details

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenlab.domain.exception.GenericErrorException
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import com.example.tokenlab.domain.use_case.GetMovieDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) :
    ViewModel() {
    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _networkError = MutableLiveData<Unit>()
    val networkError: LiveData<Unit> = _networkError

    private val _genericError = MutableLiveData<Unit>()
    val genericError: LiveData<Unit> = _genericError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(dispatcher) {
            _loading.postValue(true)
            try {
                val movieDetails = getMovieDetailsUseCase.getMovieDetails(movieId)
                _loading.postValue(false)
                _movieDetails.postValue(movieDetails)
            } catch (e: NetworkErrorException) {
                _loading.postValue(false)
                _networkError.postValue(Unit)
            }  catch (e: GenericErrorException) {
                _loading.postValue(false)
                _genericError.postValue(Unit)
            } catch (e: Exception){
                _loading.postValue(false)
                _genericError.postValue(Unit)
            }
        }
    }
}