package saint.cheshire.test_app_not_code.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saint.cheshire.test_app_not_code.data.entity.VerySimpleEntity;

import java.util.UUID;

public interface VerySimpleEntityRepository extends JpaRepository<VerySimpleEntity, UUID> {
}
