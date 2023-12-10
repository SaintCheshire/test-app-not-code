package saint.cheshire.test_app_not_code_volume_1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import saint.cheshire.specifications.test_app_not_code.v1_0_0.server.model.SaveRequest;
import saint.cheshire.test_app_not_code_volume_1.data.entity.VerySimpleEntity;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        componentModel = "spring"
)
public interface EntityMapper {

    VerySimpleEntity toEntity(SaveRequest saveRequest);

}