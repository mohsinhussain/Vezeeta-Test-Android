
package com.mohsin.vezeeta.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohsin.vezeeta.features.characters.CharacterDetailsViewModel
import com.mohsin.vezeeta.features.characters.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindsMoviesViewModel(charactersViewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun bindsMovieDetailsViewModel(characterDetailsViewModel: CharacterDetailsViewModel): ViewModel
}