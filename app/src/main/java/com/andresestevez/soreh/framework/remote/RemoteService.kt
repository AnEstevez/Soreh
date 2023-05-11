package com.andresestevez.soreh.framework.remote

import com.andresestevez.soreh.framework.remote.dto.CharacterByIdResponse
import com.andresestevez.soreh.framework.remote.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {

    @GET("{apiKey}/search/{name}")
    suspend fun searchCharactersByName(
        @Path("apiKey") apiKey: String,
        @Path("name") name: String,
    ): SearchResponse

    @GET("{apiKey}/{id}")
    suspend fun getCharacterById(
        @Path("apiKey") apiKey: String,
        @Path("id") id: Int,
    ): CharacterByIdResponse

}