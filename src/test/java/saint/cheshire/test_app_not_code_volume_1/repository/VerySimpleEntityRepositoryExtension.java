package saint.cheshire.test_app_not_code_volume_1.repository;

import saint.cheshire.test_app_not_code_volume_1.data.entity.VerySimpleEntity;
import saint.cheshire.test_app_not_code_volume_1.data.repository.VerySimpleEntityRepository;

public interface VerySimpleEntityRepositoryExtension  extends VerySimpleEntityRepository {

    VerySimpleEntity findByCode(String code);

}
