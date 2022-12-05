package jpql;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private String username;
    private int age;
}
