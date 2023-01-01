package com.example.newsapp.model.providers

import com.google.gson.annotations.SerializedName

class ProviderResponse(@SerializedName("sources") val providers:List<Provider>)