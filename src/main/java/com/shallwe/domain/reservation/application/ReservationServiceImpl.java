package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.domain.user.exception.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
//    private final GiftRepository giftRepository;


    //id 로 reservation 가져오기
    private Reservation getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(InvalidUserException::new);
        return reservation;
    }

    @Transactional
    public Reservation addReservation(Long userId, ReservationRequest reservationRequest){
        User user = userRepository.findById(userId).orElseThrow(InvalidUserException::new);
        String userName = user.getName();
        Reservation reservation = ReservationRequest.toEntity(reservationRequest,userName);

        return reservationRepository.save(reservation);
    }

    public List<ReservationResponse> getAllReservation(){
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationRes = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse reservationResponse1 = new ReservationResponse();
            reservationResponse1.setId(reservation.getId());
            //reservationRes1.setGift_id(reservation.getGift_id());
            reservationResponse1.setPersons(reservation.getPersons());
            reservationResponse1.setDate(reservation.getDate());
            reservationResponse1.setSender(reservation.getSender());
            reservationResponse1.setPhone_number(reservation.getPhone_number());
            reservationResponse1.setInvitation_img(reservation.getInvitation_img());
            reservationResponse1.setInvitation_comment(reservation.getInvitation_comment());
            reservationResponse1.setReservation_status(reservation.getReservation_status());
            reservationRes.add(reservationResponse1);
        }
        return reservationRes;
    }
    @Transactional
    public Reservation updateReservation(Long id, ReservationRequest updateRequest){

        Reservation reservation = getReservation(id);
        reservation.updateReservation(updateRequest);
        return reservation;
    }
    @Transactional
    public Reservation cancelReservation(Long id){
        Reservation reservation = getReservation(id);
        reservation.cancelReservation();
        return reservation;
    }
    @Transactional
    public void deleteReservation(Long id){
        Reservation reservation = getReservation(id);
        reservationRepository.delete(reservation);
    }


}
