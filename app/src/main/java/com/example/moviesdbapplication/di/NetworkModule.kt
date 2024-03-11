package com.example.moviesdbapplication.di

import android.content.Context
import androidx.room.Room
import com.example.moviesdbapplication.BuildConfig
import com.example.moviesdbapplication.data.local.LocalDataSource
import com.example.moviesdbapplication.data.local.db.AppDatabase
import com.example.moviesdbapplication.data.local.db.CategoryDao
import com.example.moviesdbapplication.data.local.db.MovieDao
import com.example.moviesdbapplication.data.local.db.MovieWithCategoryRelationDao
import com.example.moviesdbapplication.data.remote.MovieApiService
import com.example.moviesdbapplication.data.remote.RemoteDataSource
import com.example.moviesdbapplication.data.repository.FirebaseRepository
import com.example.moviesdbapplication.data.repository.FirebaseRepositoryImpl
import com.example.moviesdbapplication.domain.repository.GalleryRepository
import com.example.moviesdbapplication.domain.repository.MapRepository
import com.example.moviesdbapplication.domain.repository.MoviesRepository
import com.example.moviesdbapplication.domain.repository.ProfileRepository
import com.example.moviesdbapplication.domain.repository.impl.GalleryRepositoryImpl
import com.example.moviesdbapplication.domain.repository.impl.MapRepositoryImpl
import com.example.moviesdbapplication.domain.repository.impl.MoviesRepositoryImpl
import com.example.moviesdbapplication.domain.repository.impl.ProfileRepositoryImpl
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val loggingInterceptor = if (BuildConfig.DEBUG) HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
        val apiKey = BuildConfig.MOVIES_API_KEY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${apiKey}")
                    .header("accept", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            })
            .build()
        return Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    @Provides
    fun providesMoviesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideMovieWithCategoryRelationDao(appDatabase: AppDatabase): MovieWithCategoryRelationDao {
        return appDatabase.movieWithCateogryRelation()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movies_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        movieDao: MovieDao,
        categoryRelationDao: MovieWithCategoryRelationDao,
        categoryDao: CategoryDao
    ): LocalDataSource {
        return LocalDataSource(movieDao, categoryRelationDao, categoryDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(movieApiService: MovieApiService): RemoteDataSource {
        return RemoteDataSource(movieApiService)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideMapRepository(locationsCollection: CollectionReference): MapRepository {
        return MapRepositoryImpl(locationsCollection)
    }

    @Provides
    @Singleton
    fun provideLocationsCollection(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection("locations")
    }

    @Provides
    @Singleton
    fun provideUsersCollection(firestore: FirebaseFirestore): DocumentReference {
        return firestore.collection("user").document("uniqueId")
    }

    @Provides
    @Singleton
    fun provideProfileRepository(userCollection: DocumentReference): ProfileRepository {
        return ProfileRepositoryImpl(userCollection)
    }

    @Provides
    fun provideStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }
    @Provides
    fun provideStorageStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
    @Provides
    fun provideFirebaseRepository(storageReference: FirebaseStorage): FirebaseRepository {
        return FirebaseRepositoryImpl(storageReference)
    }
    @Provides
    @Singleton
    fun provideGalleryRepository(firebaseRepository: FirebaseRepository): GalleryRepository {
        return GalleryRepositoryImpl(firebaseRepository)
    }
}