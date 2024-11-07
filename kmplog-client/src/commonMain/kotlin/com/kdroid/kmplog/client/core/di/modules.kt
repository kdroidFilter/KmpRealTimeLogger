package com.kdroid.kmplog.client.core.di

import com.kdroid.kmplog.client.core.data.local.HomePreferencesRepositoryImpl
import com.kdroid.kmplog.client.core.data.local.SettinsPreferencesRepositoryImpl
import com.kdroid.kmplog.client.core.data.network.WebSocketManager
import com.kdroid.kmplog.client.core.data.network.engine
import com.kdroid.kmplog.client.core.presentation.MainViewModel
import com.kdroid.kmplog.client.core.domain.HomePreferencesRepository
import com.kdroid.kmplog.client.core.domain.SettingsPreferencesRepository
import com.kdroid.kmplog.client.core.presentation.navigation.DefaultNavigator
import com.kdroid.kmplog.client.core.presentation.navigation.Destination
import com.kdroid.kmplog.client.core.presentation.navigation.Navigator
import com.kdroid.kmplog.client.features.screens.home.HomeViewModel
import com.kdroid.kmplog.client.features.screens.settings.SettingsViewModel
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //Navigator
    single<Navigator> { DefaultNavigator(startDestination = Destination.Home) }

    //Preferences
    single { Settings() }
    single<HomePreferencesRepository> { HomePreferencesRepositoryImpl(settings = get()) }
    single<SettingsPreferencesRepository>{ SettinsPreferencesRepositoryImpl(settings = get()) }

    //WebSocket
    single {  WebSocketManager(settingsRepository = get()) }

    //ViewModels
    viewModel { MainViewModel(webSocketManager = get()) }
    viewModel { HomeViewModel(engine, navigator= get(), repository = get(), webSocketManager = get()) }
    viewModel { SettingsViewModel(repository = get(), navigator = get()) }


}

fun initKoin(modules: Module = appModule) =
    startKoin {
        modules(modules = modules)
    }
