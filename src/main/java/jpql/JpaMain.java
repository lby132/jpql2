package jpql;

import javax.persistence.*;
import java.util.List;

import static jpql.MemberType.*;

public class JpaMain {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();

        final EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            final Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            final Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            final Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            member1.setType(ADMIN);
            em.persist(member1);

            final Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            member2.setType(ADMIN);
            em.persist(member2);

            final Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            member3.setType(ADMIN);
            em.persist(member3);

            // 기본 CASE 식
//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금'" +
//                            "     when m.age <= 60 then '경로요금'" +
//                            "     else '일반요금'" +
//                            "end " +
//                    "from Member m";

            // CASE식 (사용자 이름이 없으면 이름 없는 회원을 반환함)
//            String query = "select coalesce(m.username, '이름 없는 회원') from Member m";

            // 사용자 이름이 관리자면 null을 반환하고 나머지는 본인의 이름을 반환함
            String query = "select nullif(m.username, '관리자') from Member m";


            List<String> result = em.createQuery(query, String.class).getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
