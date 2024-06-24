package ru.netology.cloudstorage.repository;

import ru.netology.cloudstorage.model.entity.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    @Query(value = "select * from tokens where auth_token = :auth_token", nativeQuery = true)
    Optional<Token> findToken(@Param("auth_token") String token);

    @Query(value = "select * from tokens where user_id = :user_id", nativeQuery = true)
    Optional<Token> findTokenToUser(@Param("user_id") Long userId);


    @Query(value = "delete from tokens where auth_token = :auth_token returning 'Success logout!'", nativeQuery = true)
    Optional<String> removeToken(@Param("auth_token") String auth_token);

}
