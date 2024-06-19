package api.documentation.restdocs.member.controller;

import api.documentation.restdocs.member.domain.Member;
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
public class MemberController {

    private final List<Member> members = new ArrayList<>();

    @PostConstruct
    public void init() {
        members.add(new Member("John", "존", 30));
        members.add(new Member("Jane", "제인", 31));
        members.add(new Member("Jack", "잭", 32));
        members.add(new Member("Wack", "왝", 33));
    }

    @GetMapping("")
    public ResponseEntity<List<Member>> getMembers() {
        return ResponseEntity.ok().body(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(
            @PathVariable("id") String id
    ) {

        Member member = members.stream()
                .filter(x -> id.equals(x.getId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("없어"));
        return ResponseEntity.ok(member);
    }
}
