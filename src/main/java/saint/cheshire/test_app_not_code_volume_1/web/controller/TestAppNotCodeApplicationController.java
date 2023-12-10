package saint.cheshire.test_app_not_code_volume_1.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;
import saint.cheshire.specifications.test_app_not_code.v1_0_0.server.api.TestAppNotCodeApplicationApiController;
import saint.cheshire.specifications.test_app_not_code.v1_0_0.server.model.FindRequest;
import saint.cheshire.specifications.test_app_not_code.v1_0_0.server.model.FindResponse;
import saint.cheshire.specifications.test_app_not_code.v1_0_0.server.model.SaveRequest;
import saint.cheshire.test_app_not_code_volume_1.data.entity.VerySimpleEntity;
import saint.cheshire.test_app_not_code_volume_1.data.repository.VerySimpleEntityRepository;
import saint.cheshire.test_app_not_code_volume_1.mapper.EntityMapper;

import java.util.Optional;

@Controller
public class TestAppNotCodeApplicationController extends TestAppNotCodeApplicationApiController {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    VerySimpleEntityRepository verySimpleEntityRepository;

    public TestAppNotCodeApplicationController(NativeWebRequest request) {
        super(request);
    }

    @Override
    public ResponseEntity<Void> save(SaveRequest saveRequest) {
        VerySimpleEntity verySimpleEntity = entityMapper.toEntity(saveRequest);
        verySimpleEntity.setStatus("New");
        verySimpleEntityRepository.save(verySimpleEntity);

        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<FindResponse> find(FindRequest findRequest) {
        Optional<VerySimpleEntity> result = verySimpleEntityRepository.findById(findRequest.getId());

        if (result.isPresent()) {
            FindResponse response = FindResponse.builder()
                    .code(result.get().getCode())
                    .status(result.get().getStatus())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound()
                    .build();
        }
    }

}
