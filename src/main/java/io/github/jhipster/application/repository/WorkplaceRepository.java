package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Workplace;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Workplace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {
    @Query("select distinct workplace from Workplace workplace left join fetch workplace.workplaces")
    List<Workplace> findAllWithEagerRelationships();

    @Query("select workplace from Workplace workplace left join fetch workplace.workplaces where workplace.id =:id")
    Workplace findOneWithEagerRelationships(@Param("id") Long id);

}
