import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import PACK.domain.Xxx;

@Repository
public interface XxxRepository extends PagingAndSortingRepository<Xxx, UUID>
{
}

