package api.documentation.swagger.member.controller;

import api.documentation.swagger.exception.GlobalExceptionHandler;
import api.documentation.swagger.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/members")
@Tag(name = "MemberController", description = "this is member controller")
public class MemberController {

    private final List<Member> members = new ArrayList<>();

    @PostConstruct
    public void init() {
        members.add(new Member("John", "john@doe.com"));
        members.add(new Member("Jane", "jane@doe.com"));
        members.add(new Member("Jack", "jack@doe.com"));
        members.add(new Member("Jack", "jack@doe.com"));
    }

    @GetMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 멤버들을 반환한다",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Member.class))),
                            @Content(mediaType = "text/html", schema = @Schema(implementation = Member.class))
                    }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(hidden = true)))
    })
    @Operation(summary = "get all members", description = "모든 멤버 조회")
    public ResponseEntity<List<Member>> getMembers() {
        return ResponseEntity.ok().body(members);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "존재하지 않는 멤버",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))
            )
    })
    public ResponseEntity<Member> getMemberById(
            @Schema(description = "아이디")
            @PathVariable("id") String id
    ) {

        Member member = members.stream()
                .filter(x -> id.equals(x.getId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("없어"));
        return ResponseEntity.ok(member);
    }
}
