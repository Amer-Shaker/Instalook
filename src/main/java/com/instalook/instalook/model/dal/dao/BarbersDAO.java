/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.instalook.instalook.model.dal.dao;

import com.instalook.instalook.model.dal.entity.Barber;
import java.util.List;

/**
 *
 * @author Aya
 */
public interface BarbersDAO {

    public static final String BARBERS_TABLE = "instalook.barbers";
    public static final String BARBER_ID_COLUMN = "barber_id";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String ROLE_COLUMN = "role";
    public static final String RATE_COLUMN = "rate";
    public static final String BARBER_PICTURE_COLUMN = "barber_picture";
    public static final String IS_AVAILABLE_COLUMN = "is_available";
    public static final String SALON_ID_COLUMN = "salon_id";

    public List<Barber> getAllBarbers(Integer salonId);

    public Barber getBarberById(Integer id);

    public int addBarber(Barber barber);

    public void updateBarberData(Barber barber);

    public void updateBarberAvailability(Integer barberId, Integer availability);

    public void rateBarber(Integer barberId, Integer rate);

    public int deleteBarber(Integer barberId);

}
