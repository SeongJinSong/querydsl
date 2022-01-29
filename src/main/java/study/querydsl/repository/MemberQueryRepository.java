package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * 특정 화면에 종속되어 있으면 별도의 조회용 리포지토리를 만드는것이 괜찮다
 */

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
}
