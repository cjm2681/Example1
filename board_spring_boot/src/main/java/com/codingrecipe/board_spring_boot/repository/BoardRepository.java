package com.codingrecipe.board_spring_boot.repository;

import com.codingrecipe.board_spring_boot.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    /*
    @Query(value = "update BoardEntity b set b.BoardHits=b.boardHits+1 where b.id=:id",nativeQuery = true)
    콤마로 nativeQuery = ture 붙여줄시 DB에서 사용되는 쿼리 사용가능
     */
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")  //Entity기준 쿼리 사용
    void updateHits(@Param("id") Long id);

}
