package com.company.evgeniy.auto_shop.autos;

import com.company.evgeniy.auto_shop.autos.entities.AutoEntity;
import org.springframework.data.repository.CrudRepository;

public interface AutosRepository extends CrudRepository<AutoEntity, Integer> {
}
