package it.corona.fabrick.component.impl;

import it.corona.fabrick.component.ClientInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientInterfaceImpl implements ClientInterface {

    private final WebClient.Builder webClientBuilder;

    @Override
    public <T> T getRequest(String uri,
                            Object[] uriVariables,
                            Map<String, ?> queryParams,
                            Map<String, String> headers,
                            ParameterizedTypeReference<T> returnType) {

        log.info("GET request to {}", uri);
        return webClientBuilder
                .build()
                .get()
                .uri(buildUri(uri, uriVariables, queryParams))
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::add);
                    }
                })
                .exchangeToMono(response -> response.bodyToMono(returnType))
                .block();
    }

    @Override
    public <T> T putRequest(String uri,
                            Object[] uriVariables,
                            Map<String, ?> queryParams,
                            Map<String, String> headers,
                            Object body,
                            ParameterizedTypeReference<T> returnType) {

        log.info("PUT request to {}", uri);
        return webClientBuilder
                .build()
                .put()
                .uri(buildUri(uri, uriVariables, queryParams))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::add);
                    }
                })
                .exchangeToMono(response -> response.bodyToMono(returnType))
                .block();
    }

    @Override
    public <T> T patchRequest(String uri,
                              Object[] uriVariables,
                              Map<String, ?> queryParams,
                              Map<String, String> headers,
                              Object body,
                              ParameterizedTypeReference<T> returnType) {

        log.info("PATCH request to {}", uri);
        return webClientBuilder
                .build()
                .patch()
                .uri(buildUri(uri, uriVariables, queryParams))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::add);
                    }
                })
                .exchangeToMono(response -> response.bodyToMono(returnType))
                .block();
    }

    @Override
    public <T> T postRequest(String uri,
                             Object[] uriVariables,
                             Map<String, ?> queryParams,
                             Map<String, String> headers,
                             Object body,
                             ParameterizedTypeReference<T> returnType) {

        log.info("POST request to {}", uri);
        return webClientBuilder
                .build()
                .post()
                .uri(buildUri(uri, uriVariables, queryParams))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::add);
                    }
                })
                .exchangeToMono(response -> response.bodyToMono(returnType))
                .block();
    }

    @Override
    public <T> T deleteRequest(String uri,
                               Object[] uriVariables,
                               Map<String, String> headers,
                               ParameterizedTypeReference<T> returnType) {

        log.info("DELETE request to {}", uri);
        return webClientBuilder
                .build()
                .delete()
                .uri(buildUri(uri, uriVariables, null))
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::add);
                    }
                })
                .exchangeToMono(response -> response.bodyToMono(returnType))
                .block();
    }

    private URI buildUri(String uri, Object[] uriVariables, Map<String, ?> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(uriBuilder::queryParam);
        }

        return uriBuilder.buildAndExpand(uriVariables).toUri();
    }
}