package com.company.evgeniy.auto_shop.users;

import com.company.evgeniy.auto_shop.users.exceptions.EmailExistsException;
import com.company.evgeniy.auto_shop.users.dto.CreateUserDto;
import com.company.evgeniy.auto_shop.users.dto.UpdateUserDto;
import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import com.company.evgeniy.auto_shop.utils.MappingUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MappingUtil mappingUtil = new MappingUtil();

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Iterable<UserEntity> getAllUsers() {
        return this.usersRepository.findAll();
    }

    public UserEntity getUserById(int id) {
        return this.usersRepository.findById(id).get();
    }

    public UserEntity createUser(CreateUserDto createUserDto) throws ParseException {
        UserEntity userEntity = mappingUtil.<UserEntity, CreateUserDto>mapDtoToEntity(createUserDto, UserEntity.class);
        UserEntity user = this.findUserByEmail(userEntity.getEmail());
        if ( user != null ) {
            throw new EmailExistsException("Email is used");
        } else {
            String hashedPassword = this.bCryptPasswordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            return this.usersRepository.save(userEntity);
        }
    }

    public UserEntity findUserByEmail(String email) {
        Iterable<UserEntity> users = this.getAllUsers();
        for ( UserEntity user : users ) {
            if ( user.getEmail().toLowerCase().equals(email.toLowerCase()) ) {
                return user;
            }
        }
        return null;
    }

    public void removeUser(int userId) {
        this.usersRepository.deleteById(userId);
    }

    public UserEntity updateUser(int userId, UpdateUserDto updateUserDto) throws ParseException {
        UserEntity userEntity = this.usersRepository.findById(userId).get();
        userEntity.setEmail(updateUserDto.getEmail());
        userEntity.setPassword(updateUserDto.getPassword());
        userEntity.setFirstName(updateUserDto.getFirstName());
        userEntity.setLastName(updateUserDto.getLastName());
        userEntity.setCountry(updateUserDto.getCountry());
        userEntity.setAvatar(updateUserDto.getAvatar());
        return this.usersRepository.save(userEntity);
    }

}
