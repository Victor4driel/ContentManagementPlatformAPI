package com.cms.project.repositories;

import com.cms.project.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT c FROM Tag c WHERE c.id IN :ids")
    List<Tag> findAllByIdIn(@Param("ids") List<UUID> ids);
}
