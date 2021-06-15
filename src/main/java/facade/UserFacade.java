package facade;

import entities.Developer;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import secuirty.errorhandling.AuthenticationException;
import security.*;


public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public Developer getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Developer developer;
        try {
            developer = em.find(Developer.class, username);
            if (developer == null || !developer.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return developer;
    }

}
