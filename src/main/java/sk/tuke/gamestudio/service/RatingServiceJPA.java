package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
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
        return entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("name", player).getFirstResult();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}
