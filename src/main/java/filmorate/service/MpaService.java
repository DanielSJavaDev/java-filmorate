package filmorate.service;

import filmorate.dao.MpaDao;
import filmorate.model.Mpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaService {
    private final MpaDao mpaDao;

    public Mpa findMpaById(int id) {
        log.info("Найден mpa " + id);
        return mpaDao.getMpa(id);
    }

    public Collection<Mpa> findAllMpa() {
        log.info("Все mpa найдены");
        return mpaDao.getMpaList();
    }
}
