package ru.netology.cloudstorage.repository;

import ru.netology.cloudstorage.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> getUsersByLogin(String login);

    boolean existsByLogin(String login);

    void removeAllByLogin(String login);
}