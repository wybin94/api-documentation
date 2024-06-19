package api.documentation.combine.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private String id;
    private String name;
    private int age;

    public Member() {
    }

    public Member(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Member(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
