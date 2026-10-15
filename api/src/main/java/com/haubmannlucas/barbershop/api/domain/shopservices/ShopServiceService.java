package com.haubmannlucas.barbershop.api.domain.shopservices;

import com.haubmannlucas.barbershop.api.domain.shopservices.dto.ShopServiceRequestDTO;
import com.haubmannlucas.barbershop.api.domain.shopservices.dto.ShopServiceResponseDTO;
import com.haubmannlucas.barbershop.api.exception.EmailAlreadyExistsException;
import com.haubmannlucas.barbershop.api.exception.NameAlreadyExistsException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ShopServiceService {

    private static final Logger logger = LoggerFactory.getLogger(ShopServiceService.class);
    private final ShopServiceRepository shopServiceRepository;

    public ShopServiceService(ShopServiceRepository shopServiceRepository) {
        this.shopServiceRepository = shopServiceRepository;
    }

    @PostMapping
    public ShopServiceResponseDTO createShopService(@Valid @RequestBody ShopServiceRequestDTO request) {

        logger.info("Starting shop service registration for name: {}", (request.serviceName()));

        if (shopServiceRepository.existsByServiceName(request.serviceName())) {
            logger.warn("Registration failed: Service Name {} is already in use", request.serviceName());
            throw new NameAlreadyExistsException();
        }

        ShopServiceEntity entity = ShopServiceEntity.builder()
                .serviceName(request.serviceName())
                .serviceDescription(request.serviceDescription())
                .serviceDuration(request.serviceDuration())
                .servicePrice(request.servicePrice())
                .active(true)
                .build();

                ShopServiceEntity savedEntity = shopServiceRepository.save(entity);
                logger.info("Service successfully registered with ID: {}", savedEntity.getId());

                return ShopServiceResponseDTO.builder()
                        .id(savedEntity.getId())
                        .serviceName(savedEntity.getServiceName())
                        .serviceDescription(savedEntity.getServiceDescription())
                        .serviceDuration(savedEntity.getServiceDuration())
                        .servicePrice(savedEntity.getServicePrice())
                        .active(savedEntity.getActive())
                        .build();
    }
}
