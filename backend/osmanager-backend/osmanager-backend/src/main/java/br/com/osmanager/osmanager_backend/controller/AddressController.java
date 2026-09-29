package br.com.osmanager.osmanager_backend.controller;


import br.com.osmanager.osmanager_backend.service.AddressService;
import br.com.osmanager.osmanager_backend.service.dto.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<AddressResponse> findByCep(
            @PathVariable String cep) {

        AddressResponse response =
                addressService.findByCep(cep);

        return ResponseEntity.ok(response);
    }
}