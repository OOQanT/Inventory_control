package test.connect.mssql.service.refresh;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.connect.mssql.entity.Refresh;
import test.connect.mssql.repository.refresh.RefreshRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshService {

    private final RefreshRepository refreshRepository;

    public Boolean existsByRefresh(String refreshToken){
        return refreshRepository.existsByRefresh(refreshToken);
    }

    public void deleteByRefresh(String refreshToken){
        refreshRepository.deleteByRefresh(refreshToken);
    }

    public Refresh newRrefresh(Refresh refresh){
        Refresh saved = refreshRepository.save(refresh);
        return saved;
    }
}
