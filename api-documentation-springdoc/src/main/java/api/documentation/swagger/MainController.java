package api.documentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "rootController @Tag name", description = "rootController @Tag description")
public class MainController {

    @GetMapping("/")
    @Operation(summary = "root @Operation summary", description = "root @Operation description")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("hello world");
    }

    @GetMapping("/{statusCode}")
    @Operation(summary = "statusCode", description = "statusCode를 넘기면 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "존재하지 않는 status Code")
    })
    public ResponseEntity<String> statusCode(
            @Schema(description = "Member ID", example = "1")
            @PathVariable int statusCode
    ) {
        HttpStatus resolvedStatusCode = HttpStatus.resolve(statusCode);
        if (resolvedStatusCode == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return new ResponseEntity<>("body", resolvedStatusCode);
    }


}
