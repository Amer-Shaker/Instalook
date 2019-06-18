package com.instalook.instalook.model.dal.dao.impl;

import com.instalook.instalook.model.dal.dto.BarberDTO;
import com.instalook.instalook.model.dal.entity.Barber;
import com.instalook.instalook.model.dal.entity.Salon;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.instalook.instalook.model.dal.dao.BarberDAO;
import org.hibernate.HibernateException;

/**
 *
 * @author Aya
 */
@Repository
@Transactional
public class BarberDAOImpl implements BarberDAO {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Override
    public List<Barber> getAllBarbers(Integer salonId) {
        Session session = sessionFactory.openSession();
        Salon currentSalon = (Salon) session.load(Salon.class, salonId);
        Criteria criteria = session.createCriteria(Barber.class);
        criteria.add(Restrictions.eq("salon", currentSalon));
        return criteria.list();
    }

    @Override
    public Barber getBarberById(Integer id) {
        return (Barber) sessionFactory.openSession().get(Barber.class, id);
    }

    @Override
    public int addBarber(BarberDTO barberDTO) {
        Session session = null;
        int salonId = 0;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Salon salon = (Salon) session.load(Salon.class, barberDTO.getSalonId());
            barberDTO.getBarber().setSalon(salon);
            salon.getBarbers().add(barberDTO.getBarber());
            salonId = (Integer) session.save(salon);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return salonId;
    }

    @Override
    public void updateBarberAvailability(Integer barberId, Integer availability) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Barber barber = (Barber) session.load(Barber.class, barberId);
        barber.setIsAvailable(availability);
        session.update(barber);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateBarberData(Barber barber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Barber updatedBarber = (Barber) session.load(Barber.class, barber.getBarberId());
        updatedBarber.setRate(barber.getRate());
        updatedBarber.setFirstName(barber.getFirstName());
        updatedBarber.setLastName(barber.getLastName());
        updatedBarber.setBarberPicture(barber.getBarberPicture());
        updatedBarber.setBookings(barber.getBookings());
        updatedBarber.setIsAvailable(barber.getIsAvailable());
        updatedBarber.setRole(barber.getRole());
        updatedBarber.setSalon(barber.getSalon());

        session.evict(updatedBarber);
        session.update(barber);
        transaction.commit();
        session.close();
    }

    @Override
    public void rateBarber(Integer barberId, Integer rate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Barber barber = (Barber) session.load(Barber.class, barberId);
        barber.setRate(rate);
        session.update(barber);
        transaction.commit();
        session.close();
    }

    @Override
    public int deleteBarber(Integer barberId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String query = "delete Barber where barberId = :id";
        int result = session.createQuery(query).setParameter("id", barberId).executeUpdate();
        transaction.commit();
        session.close();
        return result;
    }
}
