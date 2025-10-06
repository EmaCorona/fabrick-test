package it.corona.fabrick.component;

import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;

public interface ClientInterface {

    default <T> T getRequest(String uri, Object[] uriVariables, ParameterizedTypeReference<T> returnType) {
        return getRequest(uri, uriVariables, Map.of(), Map.of(), returnType);
    }

    default <T> T putRequest(String uri, Object[] uriVariables, Object body, ParameterizedTypeReference<T> returnType) {
        return putRequest(uri, uriVariables, Map.of(), Map.of(), body, returnType);
    }

    default <T> T patchRequest(String uri, Object[] uriVariables, Object body, ParameterizedTypeReference<T> returnType) {
        return patchRequest(uri, uriVariables, Map.of(), Map.of(), body, returnType);
    }

    default <T> T postRequest(String uri, Object[] uriVariables, Object body, ParameterizedTypeReference<T> returnType) {
        return postRequest(uri, uriVariables, Map.of(), Map.of(), body, returnType);
    }

    <T> T getRequest(String uri,
                     Object[] uriVariables,
                     Map<String, ?> queryParams,
                     Map<String, String> headers,
                     ParameterizedTypeReference<T> returnType);

    <T> T putRequest(String uri,
                     Object[] uriVariables,
                     Map<String, ?> queryParams,
                     Map<String, String> headers,
                     Object body,
                     ParameterizedTypeReference<T> typeReference);

    <T> T patchRequest(String uri,
                       Object[] uriVariables,
                       Map<String, ?> queryParams,
                       Map<String, String> headers,
                       Object body,
                       ParameterizedTypeReference<T> typeReference);

    <T> T postRequest(String uri,
                      Object[] uriVariables,
                      Map<String, ?> queryParams,
                      Map<String, String> headers,
                      Object body,
                      ParameterizedTypeReference<T> typeReference);

    <T> T deleteRequest(String uri,
                        Object[] uriVariables,
                        Map<String, String> headers,
                        ParameterizedTypeReference<T> returnType);
}
