package ru.netology.cloudstorage.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.model.entity.FileEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query(value = "select * from files where user_id = :user_id limit :limit", nativeQuery = true)
    List<FileEntity> getFiles(@Param("limit") int limit,
                           @Param("user_id") Long userId);

    @Query(value = "select f from FileEntity f where f.name = :name")
    FileEntity findByName(String name);
}