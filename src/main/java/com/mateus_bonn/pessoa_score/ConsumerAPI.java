package com.mateus_bonn.pessoa_score;

public interface ConsumerAPI<T>{

    T consumerAPIAddress(String url, String cep);
}
