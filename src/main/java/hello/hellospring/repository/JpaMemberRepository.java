package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{
    // jpa는 모든것이 entity manager를 통해서 진행된다.
    // database와 connection 정보를 가지고 모든 것을 처리해줌.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // 영구 저장하는 것
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 조회(조회할 타입, 식별자)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // JPQL 객체지향 Query 언어를 사용해야함
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // JPQL query 언어, 객체(Member entity)를 대상으로 query를 날리고, sql 로 번역이 된다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();

    }
}
