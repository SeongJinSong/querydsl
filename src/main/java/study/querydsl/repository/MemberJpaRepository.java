package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static study.querydsl.entity.QMember.member;

@Repository
//@RequiredArgsConstructor // 빈으로 등록하면 이걸 편하게 할 수 있다.
public class MemberJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    /**
     * JPAQueryFactory에 대한 동시성 문제는 EntityManager에 의존한다.
     * EM은 진짜 영속성 컨텍스트가 아니라 Proxy인 가짜를 주입해주고, 트랜잭션 단위로 다른데로 바인딩되도록 라우팅해준다!
     */

    public MemberJpaRepository(EntityManager em){//, JPAQueryFactory queryFactory) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em); // 이걸 스프링빈으로 등록해도 된다.
//        this.queryFactory = queryFactory;
    }

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public List<Member> findAll_querydsl(){
        return queryFactory
                .selectFrom(member)
                .fetch();
    }
    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
    public List<Member> findByUsername_querydsl(String username){
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }
}
