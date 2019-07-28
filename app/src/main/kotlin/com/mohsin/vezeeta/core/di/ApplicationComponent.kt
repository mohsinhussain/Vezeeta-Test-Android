/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohsin.vezeeta.core.di

import com.mohsin.vezeeta.AndroidApplication
import com.mohsin.vezeeta.core.di.viewmodel.ViewModelModule
import com.mohsin.vezeeta.features.characters.MovieDetailsFragment
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
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
