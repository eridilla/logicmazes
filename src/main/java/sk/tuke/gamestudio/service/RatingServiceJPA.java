package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.createNamedQuery("Rating.deleteRating").setParameter("game", rating.getGame()).setParameter("name", rating.getName()).executeUpdate();
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Object obj = entityManager.createNamedQuery("Rating.getAvgRating").setParameter("game", game).getSingleResult();

        if (obj == null) {
            return -1;
        } else {
            double ret = Double.parseDouble(obj.toString());
            return (int) Math.round(ret);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        List<Rating> var = entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("name", player).getResultList();

        if (var.size() == 0) {
            return -1;
        } else {
            return var.get(0).getRating();
        }
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}
