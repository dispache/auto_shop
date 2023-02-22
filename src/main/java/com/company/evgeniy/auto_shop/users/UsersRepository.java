package com.company.evgeniy.auto_shop.users;

import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Integer> {
}
