package com.goda.marvel.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

val MARVEL_CHARACTER_API_DEP: MarvelCharacterApi by lazy {
    retrofitClient.create(MarvelCharacterApi::class.java)
}

interface MarvelCharacterApi {
    @GET("v1/public/characters")
    fun getCharactersList(@Query("limit") limit: Int, @Query("offset") start: Int, @Query("apikey") apiKey: String, @Query("hash") hash: String = "9106e28760a2e66f0d39c70e9f629270", @Query("ts") ts: String = "1"): Single<CharactersResponse>

    @GET("v1/public/characters")
    fun getSearchCharacterList(@Query("nameStartsWith") nameStartsWith: String, @Query("limit") limit: Int, @Query("offset") start: Int, @Query("apikey") apiKey: String, @Query("hash") hash: String = "9106e28760a2e66f0d39c70e9f629270", @Query("ts") ts: String = "1"): Single<CharactersResponse>

    @GET("v1/public/characters/{characterId}/comics")
    fun getComicsCharacterList(@Path("characterId") characterId: Int, @Query("apikey") apiKey: String = "7762cfbc02255884a47028f5bd6fb7e6", @Query("hash") hash: String = "9106e28760a2e66f0d39c70e9f629270", @Query("ts") ts: String = "1"): Single<CessResponse>

    @GET("v1/public/characters/{characterId}/events")
    fun getEventsCharacterList(@Path("characterId") characterId: Int, @Query("apikey") apiKey: String = "7762cfbc02255884a47028f5bd6fb7e6", @Query("hash") hash: String = "9106e28760a2e66f0d39c70e9f629270", @Query("ts") ts: String = "1"): Single<CessResponse>
    @GET("v1/public/characters/{characterId}/series")
    fun getSeriesCharacterList(@Path("characterId") characterId: Int, @Query("apikey") apiKey: String = "7762cfbc02255884a47028f5bd6fb7e6", @Query("hash") hash: String = "9106e28760a2e66f0d39c70e9f629270", @Query("ts") ts: String = "1"): Single<CessResponse>
    @GET("v1/public/characters/{characterId}/stories")
    fun getStoriesCharacterList(@Path("characterId") characterId: Int, @Query("apikey") apiKey: String = "7762cfbc02255884a47028f5bd6fb7e6", @Query("hash") hash: String = "9106e28760a2e66f0d39c70e9f629270", @Query("ts") ts: String = "1"): Single<CessResponse>

}




data class CharactersResponse(

        @SerializedName("code") val code: Int,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("attributionText") val attributionText: String,
        @SerializedName("attributionHTML") val attributionHTML: String,
        @SerializedName("etag") val etag: String,
        @SerializedName("data") val data: Data
){
    constructor() : this(0, "", "", "", "","", Data(1,1,1,1, emptyList()))
}

data class CessResponse(

        @SerializedName("code") val code: Int,
        @SerializedName("status") val status: String,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("attributionText") val attributionText: String,
        @SerializedName("attributionHTML") val attributionHTML: String,
        @SerializedName("etag") val etag: String,
        @SerializedName("data") val data: DataCess
){
    constructor() : this(0, "", "", "", "","", DataCess(1,1,1,1, emptyList()))
}

@Parcelize
data class Character(

        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("thumbnail") val thumbnail: Thumbnail,
        @SerializedName("comics") val comics: Comics,
        @SerializedName("series") val series: Series,
        @SerializedName("stories") val stories: Stories,
        @SerializedName("events") val events: Events,
        @SerializedName("urls") val urls: List<Urls>
) : Parcelable {
    constructor() : this(0, "", "",  Thumbnail("", ""),  Comics(0, "", emptyList(), 0),
            Series(0, "", emptyList(), 0),
            Stories(0, "", emptyList(), 0),
            Events(0, "", emptyList(), 0)
            , emptyList())

}

@Parcelize
data class Comics(

        @SerializedName("available") val available: Int,
        @SerializedName("collectionURI") val collectionURI: String,
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int
) : Parcelable

@Parcelize
data class Data(

        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val characters: List<Character>
) : Parcelable

@Parcelize
data class DataCess(

        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val cessresults: List<CESSResult>
) : Parcelable

@Parcelize
data class Events(

        @SerializedName("available") val available: Int,
        @SerializedName("collectionURI") val collectionURI: String,
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int
) : Parcelable

@Parcelize
data class Item(

        @SerializedName("resourceURI") val resourceURI: String,
        @SerializedName("name") val name: String
) : Parcelable

@Parcelize
data class Stories(

        @SerializedName("available") val available: Int,
        @SerializedName("collectionURI") val collectionURI: String,
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int
) : Parcelable

@Parcelize
data class Thumbnail(

        @SerializedName("path") val path: String,
        @SerializedName("extension") val extension: String
) : Parcelable

@Parcelize

data class Urls(

        @SerializedName("type") val type: String,
        @SerializedName("url") val url: String
) : Parcelable

@Parcelize
data class Series(

        @SerializedName("available") val available: Int,
        @SerializedName("collectionURI") val collectionURI: String,
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int
) : Parcelable

@Parcelize
data class CESSResult(

        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,

        @SerializedName("pageCount") val pageCount: Int,
        @SerializedName("urls") val urls: List<Urls>,
        @SerializedName("thumbnail") val thumbnail: Thumbnail
) : Parcelable