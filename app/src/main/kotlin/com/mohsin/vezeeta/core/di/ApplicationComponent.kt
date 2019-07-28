
package com.mohsin.vezeeta.core.di

import com.mohsin.vezeeta.AndroidApplication
import com.mohsin.vezeeta.core.di.viewmodel.ViewModelModule
import com.mohsin.vezeeta.features.characters.CharacterDetailsFragment
import com.mohsin.vezeeta.features.characters.CharacterListFragment
import com.mohsin.vezeeta.core.navigation.RouteActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(characterListFragment: CharacterListFragment)
    fun inject(characterDetailsFragment: CharacterDetailsFragment)
}
