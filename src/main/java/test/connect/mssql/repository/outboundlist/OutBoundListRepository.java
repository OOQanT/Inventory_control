package test.connect.mssql.repository.outboundlist;

import org.springframework.data.jpa.repository.JpaRepository;
import test.connect.mssql.entity.OutboundList;

public interface OutBoundListRepository extends JpaRepository<OutboundList,Long> {
}
