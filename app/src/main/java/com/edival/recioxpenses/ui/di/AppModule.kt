package com.edival.recioxpenses.ui.di

import com.edival.recioxpenses.data.repository.WorkDayRepositoryImpl
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideWorkDayService(impl: WorkDayRepositoryImpl): WorkDayRepository = impl
}