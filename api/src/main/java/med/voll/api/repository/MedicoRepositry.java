package med.voll.api.repository;

import med.voll.api.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepositry extends JpaRepository<Medico, Long> {

}