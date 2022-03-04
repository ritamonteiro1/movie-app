package com.example.tokenlab.di

import com.example.tokenlab.data.api.Api
import com.example.tokenlab.data.api.MovieDataService
import com.example.tokenlab.data.remote.remote_data_source.MovieRemoteDataSource
import com.example.tokenlab.data.remote.remote_data_source.MovieRemoteDataSourceImpl
import com.example.tokenlab.data.repository.MovieRepositoryImpl
import com.example.tokenlab.domain.repository.MovieRepository
import com.example.tokenlab.domain.use_case.GetMovieDetailsUseCase
import com.example.tokenlab.domain.use_case.GetMovieDetailsUseCaseImpl
import com.example.tokenlab.domain.use_case.GetMovieListUseCase
import com.example.tokenlab.domain.use_case.GetMovieListUseCaseImpl
import com.example.tokenlab.presentation.movie.MovieListViewModel
import com.example.tokenlab.presentation.movie_details.MovieDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun getMovieDataService(): MovieDataService =
        Api.setupRetrofit().create(MovieDataService::class.java)
    @Provides
    fun getMovieRemoteDataSource(movieDataService: MovieDataService): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(movieDataService)
    @Provides
    fun getMovieRepository(movieRemoteDataSource: MovieRemoteDataSource): MovieRepository =
        MovieRepositoryImpl(movieRemoteDataSource)
    @Provides
    fun getMovieListUseCase(movieRepository: MovieRepository): GetMovieListUseCase =
        GetMovieListUseCaseImpl(movieRepository)
    @Provides
    fun getMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCaseImpl(movieRepository)
    @Provides
    fun getMovieListViewModel(getMovieListUseCase: GetMovieListUseCase) =
        MovieListViewModel(getMovieListUseCase)
    @Provides
    fun getMovieDetailsViewModel(getMovieDetailsUseCase: GetMovieDetailsUseCase) =
        MovieDetailsViewModel(getMovieDetailsUseCase)
}