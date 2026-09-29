package br.com.osmanager.osmanager_backend.service;


import br.com.osmanager.osmanager_backend.exception.BusinessRuleException;
import br.com.osmanager.osmanager_backend.exception.ExternalApiException;
import br.com.osmanager.osmanager_backend.service.dto.AddressResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AddressService {

    private final RestClient restClient;

    public AddressService(RestClient restClient) {
        this.restClient = restClient;
    }

    public AddressResponse findByCep(String cep) {

        String cleanCep = cep.replaceAll("\\D", "");

        if (cleanCep.length() != 8) {
            throw new BusinessRuleException(
                    "CEP deve possuir 8 dígitos"
            );
        }

        AddressResponse response = restClient.get()
                .uri(
                        "https://viacep.com.br/ws/{cep}/json/",
                        cleanCep
                )
                .retrieve()
                .body(AddressResponse.class);

        if (response == null || response.getCep() == null) {
            throw new ExternalApiException(
                    "CEP não encontrado"
            );
        }

        return response;
    }
}
