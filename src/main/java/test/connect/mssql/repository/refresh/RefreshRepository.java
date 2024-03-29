package test.connect.mssql.repository.refresh;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import test.connect.mssql.entity.Refresh;

public interface RefreshRepository extends JpaRepository<Refresh,Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
