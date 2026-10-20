package com.haubmannlucas.barbershop.api.domain.shopservices;

import com.haubmannlucas.barbershop.api.domain.shopservices.dto.ShopServiceRequestDTO;
import com.haubmannlucas.barbershop.api.domain.shopservices.dto.ShopServiceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop-services")
public class ShopServiceController {

    private final ShopServiceService shopServiceService;

    public ShopServiceController(ShopServiceService shopServiceService) {
        this.shopServiceService = shopServiceService;
    }

    @PostMapping()
    public ResponseEntity<ShopServiceResponseDTO> registerShopService(@Valid @RequestBody ShopServiceRequestDTO requestDto){
        ShopServiceResponseDTO responseDTO = shopServiceService.createShopService(requestDto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
