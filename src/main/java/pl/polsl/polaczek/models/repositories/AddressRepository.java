package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
