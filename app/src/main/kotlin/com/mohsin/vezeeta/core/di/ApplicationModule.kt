
package com.mohsin.vezeeta.core.di

import android.content.Context
import androidx.room.Room
import com.mohsin.vezeeta.AndroidApplication
import com.mohsin.vezeeta.BuildConfig
import com.mohsin.vezeeta.core.db.Database
import com.mohsin.vezeeta.features.characters.CharactersDao
import com.mohsin.vezeeta.features.characters.CharactersRepository
import com.mohsin.vezeeta.features.characters.ResourceDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun providesDatabase(): Database =
            Room.databaseBuilder(application, Database::class.java, "marvel_db").fallbackToDestructiveMigration().build()

    @Provides @Singleton fun provideMoviesRepository(dataSource: CharactersRepository.Network): CharactersRepository = dataSource

    @Provides
    @Singleton
    fun provideCharactersDao(db: Database): CharactersDao = db.charactersDao()

    @Provides
    @Singleton
    fun provideResourceDao(db: Database): ResourceDao = db.resourceDao()
}
