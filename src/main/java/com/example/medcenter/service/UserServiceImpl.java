package com.example.medcenter.service;

import com.example.medcenter.dto.PatientVisitsDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.DoctorsTypeEntity;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.QueueRepository;
import com.example.medcenter.repoitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    QueueRepository queueRepository;

    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;

    @Override
    public UsersEntity findUserByUsername(String username) {
        return usersRepository.findUsersEntityByUsername(username);
    }

    @Override
    public List<PatientVisitsDTO> getVisitsByUserId(long id) {
        List<PatientVisitsDTO> visitsList = new ArrayList<>();
        List<QueueEntity> queueEntities = queueRepository.findQueueEntitiesByUserId(id);

        int order=1;
        for(QueueEntity queue : queueEntities) {
            if(queue.getStatus() != 0){

                PatientVisitsDTO visit = new PatientVisitsDTO();
                visit.setDate(queue.getDate().toString());
                visit.setDoctorId(queue.getDoctorId());
                DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(queue.getDoctorId());
                int i = 0;
                String type = "";
                for (DoctorsTypeEntity doctorType : doctor.getDoctorsTypeEntities()) {
                    if (i == 0) {
                        type += doctorType.getType();
                    } else {
                        type += (", " + doctorType.getType());
                    }
                    i++;
                }
                visit.setDoctorType(type);
                visit.setDoctorFullname(doctor.getUsersByDoctorId().getName() + " " + doctor.getUsersByDoctorId().getSurname());
                visit.setId(order);

                Time doctorStartTime = doctor.getStartTime();
                LocalTime startTime = doctorStartTime.toLocalTime();
                startTime = startTime.plusMinutes(order * doctor.getIntervalByIntervalId().getInterval());

                visit.setStartTime(startTime.toString());

                Date today = new Date();
                if(today.before(queue.getDate())){
                    visit.setCanBeCanceled(true);
                }
                else{
                    if (today.equals(visit.getDate())){
                        visit.setCanBeCanceled(LocalTime.now().isBefore(startTime));
                    }
                    else{
                        visit.setCanBeCanceled(false);
                    }
                }


                visit.setQueueId(queue.getId());

                visitsList.add(visit);
                order++;
            }
        }

        return visitsList;
    }

    @Override
    public boolean haveQueueOrder(java.sql.Date date ,Long userId,int doctorId) {
        List<QueueEntity> queueForToday = queueRepository.findQueueEntitiesByDate(date);
        for(QueueEntity queue : queueForToday){
            if(queue.getUserId() == userId && queue.getStatus()!=0 && queue.getDoctorId()==doctorId){
                return true;
            }
        }
        return false;
    }


}
