package tn.esprit.spring.services.inters;

import java.util.Optional;

import tn.esprit.spring.dao.entities.ERole;
import tn.esprit.spring.dao.entities.Role;

public interface IRoleService {
	  Optional<Role> findByName(ERole name);

}
