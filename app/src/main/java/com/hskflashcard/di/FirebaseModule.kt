package com.hskflashcard.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.Content
import com.google.firebase.vertexai.type.GenerationConfig
import com.google.firebase.vertexai.vertexAI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirebaseVertexAIModel(): GenerativeModel = Firebase.vertexAI.generativeModel(
        modelName = "gemini-1.5-pro",
        systemInstruction = Content.Builder().text(
            "You are a Mandarin teacher for the user currently preparing for the HSK exam. You will be given a single word from the HSK word list, and your task is to explain the word and create examples in sentences or short stories based on real-world scenario. Give your answer in this JSON format:\n" +
                    "\n" +
                    "{\n" +
                    "   \"explanation\": \"\",\n" +
                    "   \"examples\": \"\",\n" +
                    "}"
        ).build(),
        generationConfig = GenerationConfig.Builder().apply {
            temperature = 1F
            topP = 0.95F
            topK = 40
            maxOutputTokens = 8192
            responseMimeType = "text/plain"
        }.build()
    )

}