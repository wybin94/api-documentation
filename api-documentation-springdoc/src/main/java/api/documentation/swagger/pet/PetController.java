package api.documentation.swagger.pet;

import ch.qos.logback.core.model.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/pets")
@Tag(name = "PetController")
public class PetController {

    @GetMapping("/")
    @Operation(summary = "get all pets", description = "모든 반려동물 조회")
    public ResponseEntity<Map<String, String>> pets() {
        return ResponseEntity.ok(
                Map.of("dog", "왈왈")
        );
    }
}
