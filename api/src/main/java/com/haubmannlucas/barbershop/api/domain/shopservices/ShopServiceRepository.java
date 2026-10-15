package com.haubmannlucas.barbershop.api.domain.shopservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopServiceRepository extends JpaRepository<ShopServiceEntity, Long> {
    boolean existsByServiceName(String name);
}
