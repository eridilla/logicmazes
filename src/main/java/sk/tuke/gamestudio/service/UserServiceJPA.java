package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.UserTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserServiceJPA implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(UserTable userTable) throws UserException {
        entityManager.createNamedQuery("UserTable.deleteUser").setParameter("username", userTable.getUsername()).setParameter("password", userTable.getPassword()).executeUpdate();
        entityManager.persist(userTable);
    }

    @Override
    public UserTable getUser(String username) throws UserException {
        List<UserTable> obj = entityManager.createNamedQuery("UserTable.getUserInfo").setParameter("username", username).getResultList();

        if (obj.size() == 0) {
            return null;
        } else {
            return obj.get(0);
        }
    }

    @Override
    public void reset() throws UserException {
        entityManager.createNamedQuery("UserTable.resetUsers").executeUpdate();
    }
}
